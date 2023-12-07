package AlexBorzilov.todoApplication.exception;

import java.util.List;

import AlexBorzilov.todoApplication.error.ErrorCodes;
import AlexBorzilov.todoApplication.error.ValidationConstants;
import AlexBorzilov.todoApplication.response.BaseSuccessResponse;
import AlexBorzilov.todoApplication.response.CustomSuccessResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {
    private final boolean success = true;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleNotReadableMessageException() {
        return new ResponseEntity<>(new BaseSuccessResponse(
                ErrorCodes.determineErrorCode(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION), success),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<?> handleNotSupportedRequestMethodException() {
        return new ResponseEntity<>(new CustomSuccessResponse<>(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION,
                ErrorCodes.determineErrorCode(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION), success),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(MissingServletRequestParameterException e) {
        List<String> errorList = e
                .getBody()
                .getDetail()
                .lines()
                .toList();
        List<Integer> errorCodeList = errorList
                .stream()
                .map(ErrorCodes::determineErrorCode)
                .distinct()
                .toList();
        return new ResponseEntity<>(new CustomSuccessResponse<>(errorList, errorCodeList.get(0), success),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleBusinessException() {
        return new ResponseEntity<>(new CustomSuccessResponse<>(ValidationConstants.TASK_NOT_FOUND,
                ErrorCodes.determineErrorCode(ValidationConstants.TASK_NOT_FOUND), success), HttpStatus.I_AM_A_TEAPOT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidateException() {
        return new ResponseEntity<>(new CustomSuccessResponse<>(ValidationConstants.PAGE_SIZE_NOT_VALID,
                ErrorCodes.determineErrorCode(ValidationConstants.PAGE_SIZE_NOT_VALID), success), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(ConstraintViolationException e) {
        List<String> errorList = e
                .getMessage()
                .lines()
                .toList();
        List<Integer> errorCodeList = errorList
                .stream()
                .map(ErrorCodes::determineErrorCode)
                .distinct()
                .toList();
        return new ResponseEntity<>(new CustomSuccessResponse<>(errorList, errorCodeList.get(0), success),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(MethodArgumentNotValidException e) {
        List<String> errorList = e
                .getBody()
                .getDetail()
                .lines()
                .toList();
        List<Integer> errorCodeList = errorList
                .stream()
                .map(ErrorCodes::determineErrorCode)
                .distinct()
                .toList();
        return new ResponseEntity<>(new CustomSuccessResponse<>(errorList, errorCodeList.get(0), success),
                HttpStatus.BAD_REQUEST);
    }
}
