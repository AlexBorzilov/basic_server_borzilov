package AlexBorzilov.todoApplication.controller;

import AlexBorzilov.todoApplication.dto.ChangeStatusTodoDto;
import AlexBorzilov.todoApplication.dto.CreateTodoDto;
import AlexBorzilov.todoApplication.exception.RestExceptionHandler;
import AlexBorzilov.todoApplication.exception.AppException;
import AlexBorzilov.todoApplication.service.TodoService;

import jakarta.validation.Valid;
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
    public ResponseEntity<?> getPaginated(@RequestParam @Positive Integer page, @RequestParam @Positive Integer perPage,
                                          @RequestParam(required = false) Boolean status) {
        return ResponseEntity.ok(todoService.getPaginated(page, perPage, status));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllReady() {
        return ResponseEntity.ok(todoService.deleteAllReady());
    }

    @PatchMapping
    public ResponseEntity<?> patch(@RequestBody ChangeStatusTodoDto statusTodoDto) throws AppException {
        return ResponseEntity.ok(todoService.patch(statusTodoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Positive long id) throws AppException {
        return ResponseEntity.ok(todoService.delete(id));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> patchStatus(@PathVariable("id") @Positive long id) throws AppException {
        return ResponseEntity.ok(todoService.patchStatus(id));
    }

    @PatchMapping("/text/{id}")
    public ResponseEntity<?> patchText(@PathVariable("id") @Positive long id, @RequestParam @Valid String text) throws AppException {
        return ResponseEntity.ok(todoService.patchText(id, text));
    }
}
