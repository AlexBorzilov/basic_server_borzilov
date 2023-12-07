package AlexBorzilov.todoApplication.service;

import java.util.List;

import AlexBorzilov.todoApplication.dto.ChangeStatusTodoDto;
import AlexBorzilov.todoApplication.dto.CreateTodoDto;
import AlexBorzilov.todoApplication.dto.GetNewsDto;
import AlexBorzilov.todoApplication.entity.TasksEntity;
import AlexBorzilov.todoApplication.exception.AppException;
import AlexBorzilov.todoApplication.repository.TasksRepo;
import AlexBorzilov.todoApplication.response.BaseSuccessResponse;
import AlexBorzilov.todoApplication.response.CustomSuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Service
@RequiredArgsConstructor
public class TodoService {
    //TODO через конструктор лучше, есть три типа внедрения
    private final TasksRepo tasksRepo;

    public CustomSuccessResponse<TasksEntity> createTodo(CreateTodoDto task) {
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setText(task.getText());
        tasksRepo.save(tasksEntity);
        return new CustomSuccessResponse<>(tasksEntity);
    }

    public CustomSuccessResponse<GetNewsDto<List<TasksEntity>>> getPaginated(Integer page, Integer perPage, Boolean status) {
        List<TasksEntity> tasksEntityList;
        int notReady = 0;
        int ready = 0;
        if (status == null) {
            tasksEntityList = tasksRepo
                    .findAll(PageRequest.of(page - 1, perPage))
                    .toList();
            for (TasksEntity t : tasksEntityList) {
                if (!t.isStatus()) {
                    notReady++;
                }
                else {
                    ready++;
                }
            }
        }
        else {
            tasksEntityList = tasksRepo
                    .findAll()
                    .stream()
                    .filter(t -> t.isStatus() == status)
                    .skip(page - 1)
                    .limit(perPage)
                    .toList();
            if (!status) {
                notReady = tasksEntityList.size();
            }
            else {
                ready = tasksEntityList.size();
            }
        }
        int numberOfElements = tasksEntityList.size();
        return new CustomSuccessResponse<>(new GetNewsDto<>(tasksEntityList, notReady, numberOfElements, ready));
    }

    @Transactional
    public BaseSuccessResponse deleteAllReady() {
        tasksRepo.deleteAllWithTrueStatus();
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patch(ChangeStatusTodoDto statusTodoDto) throws AppException {
        if (tasksRepo.findAll().isEmpty()) {
            throw new AppException("Task not found");
        }
        tasksRepo.findAll().stream().filter(t -> t.isStatus() == statusTodoDto.getStatus()).forEach(t -> {
            t.setStatus(!statusTodoDto.getStatus());
            tasksRepo.save(t);
        });
        return new BaseSuccessResponse();
    }

    @Transactional
    public BaseSuccessResponse delete(long id) throws AppException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new AppException("Task not found");
        }
        tasksRepo.deleteById(id);
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patchStatus(long id) throws AppException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new AppException("Task not found");
        }
        tasksRepo.findById(id).ifPresent(t -> {
            t.setStatus(!t.isStatus());
            tasksRepo.save(t);
        });
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patchText(long id, String text) throws AppException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new AppException("Task not found");
        }
        tasksRepo.findById(id).ifPresent(tasksEntity -> {
            tasksEntity.setText(text);
            tasksRepo.save(tasksEntity);
        });
        return new BaseSuccessResponse();
    }
}
