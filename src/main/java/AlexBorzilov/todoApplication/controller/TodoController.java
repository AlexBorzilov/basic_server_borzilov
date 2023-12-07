package AlexBorzilov.todoApplication.controller;

import AlexBorzilov.todoApplication.dto.CreateTodoDto;
import AlexBorzilov.todoApplication.exception.RestExceptionHandler;
import AlexBorzilov.todoApplication.exception.TaskNotFoundException;
import AlexBorzilov.todoApplication.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@Validated
public class TodoController extends RestExceptionHandler {
    @Autowired
    private TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTodo(@RequestBody CreateTodoDto task) {
        return ResponseEntity.ok(todoService.createTodo(task));
    }

    @GetMapping
    public ResponseEntity<?> getPaginated(@RequestParam Integer page, @RequestParam Integer perPage,
                                          @RequestParam(required = false) Boolean status) {
        return ResponseEntity.ok(todoService.getPaginated(page, perPage, status));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllReady() {
        return ResponseEntity.ok(todoService.deleteAllReady());
    }

    @PatchMapping
    public ResponseEntity<?> patch(@RequestParam boolean status) throws TaskNotFoundException {
        return ResponseEntity.ok(todoService.patch(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) throws TaskNotFoundException {
        return ResponseEntity.ok(todoService.delete(id));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> patchStatus(@PathVariable("id") long id) throws TaskNotFoundException {
        return ResponseEntity.ok(todoService.patchStatus(id));
    }

    @PatchMapping("/text/{id}")
    public ResponseEntity<?> patchText(@PathVariable("id") long id, @RequestParam String text) throws TaskNotFoundException {
        return ResponseEntity.ok(todoService.patchText(id, text));
    }
}
