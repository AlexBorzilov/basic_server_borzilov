package AlexBorzilov.todoApplication.service;

import java.util.List;

import AlexBorzilov.todoApplication.dto.CreateTodoDto;
import AlexBorzilov.todoApplication.dto.GetNewsDto;
import AlexBorzilov.todoApplication.entity.TasksEntity;
import AlexBorzilov.todoApplication.exception.TaskNotFoundException;
import AlexBorzilov.todoApplication.repository.TasksRepo;
import AlexBorzilov.todoApplication.response.BaseSuccessResponse;
import AlexBorzilov.todoApplication.response.CustomSuccessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

@Service
public class TodoService {
    //TODO через конструктор лучше, есть три типа внедрения
    private final TasksRepo tasksRepo;

    @Autowired
    public TodoService(TasksRepo tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

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

    public BaseSuccessResponse deleteAllReady() {
        tasksRepo.deleteAllWithTrueStatus();
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patch(boolean status) throws TaskNotFoundException {
        if (tasksRepo.findAll().isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        tasksRepo.findAll().stream().filter(t -> t.isStatus() == status).forEach(t -> {
            t.setStatus(!status);
            tasksRepo.save(t);
        });
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse delete(long id) throws TaskNotFoundException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        tasksRepo.deleteById(id);
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patchStatus(long id) throws TaskNotFoundException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        tasksRepo.findById(id).ifPresent(t -> {
            t.setStatus(!t.isStatus());
            tasksRepo.save(t);
        });
        return new BaseSuccessResponse();
    }

    public BaseSuccessResponse patchText(long id, String text) throws TaskNotFoundException {
        if (tasksRepo.findById(id).isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        tasksRepo.findById(id).ifPresent(tasksEntity -> {
            tasksEntity.setText(text);
            tasksRepo.save(tasksEntity);
        });
        return new BaseSuccessResponse();
    }
}
