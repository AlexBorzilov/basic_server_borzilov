package AlexBorzilov.todoApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }
}
