package AlexBorzilov.todoApplication.controller;

import AlexBorzilov.todoApplication.dto.ChangeStatusTodoDto;
import AlexBorzilov.todoApplication.dto.ChangeTextTodoDto;
import AlexBorzilov.todoApplication.dto.CreateTodoDto;
import AlexBorzilov.todoApplication.error.ValidationConstants;
import AlexBorzilov.todoApplication.exception.RestExceptionHandler;
import AlexBorzilov.todoApplication.exception.AppException;
import AlexBorzilov.todoApplication.service.TodoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@Validated
@RequiredArgsConstructor
public class TodoController extends RestExceptionHandler {
    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTodo(@RequestBody @Valid CreateTodoDto task) {
        return ResponseEntity.ok(todoService.createTodo(task));
    }

    @GetMapping
    public ResponseEntity<?> getPaginated(@RequestParam @Max(400) @Positive Integer page,
                                          @RequestParam @Max(400) @Positive Integer perPage,
                                          @RequestParam(required = false) Boolean status) {
        return ResponseEntity.ok(todoService.getPaginated(page, perPage, status));
    }

    @DeleteMapping//deletes ready(success = true) tasks
    public ResponseEntity<?> deleteAllReady() {
        return ResponseEntity.ok(todoService.deleteAllReady());
    }

    @PatchMapping//searches for tasks by success and changes it to the opposite one
    public ResponseEntity<?> patch(@RequestBody @Valid ChangeStatusTodoDto statusTodoDto) throws AppException {
        return ResponseEntity.ok(todoService.patch(statusTodoDto));
    }

    @DeleteMapping("/{id}") //deletes the task found by ID
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE) long id) throws AppException {
        return ResponseEntity.ok(todoService.delete(id));
    }

    @PatchMapping("/status/{id}") //changes the text of the task found by ID
    public ResponseEntity<?> patchStatus(@RequestBody @Valid ChangeStatusTodoDto changeStatusTodoDto, @PathVariable("id") @Positive long id) throws AppException {
        return ResponseEntity.ok(todoService.patchStatus(changeStatusTodoDto, id));
    }

    @PatchMapping("/text/{id}") //changes the text of the task found by ID
    public ResponseEntity<?> patchText(@PathVariable("id") @Positive long id,
                                       @RequestBody @Valid ChangeTextTodoDto changeTextTodoDto) throws AppException {
        return ResponseEntity.ok(todoService.patchText(changeTextTodoDto, id));
    }
}
