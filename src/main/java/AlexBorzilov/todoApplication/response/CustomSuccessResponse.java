package AlexBorzilov.todoApplication.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSuccessResponse<T> {
    private T data;
    private T codes;
    private boolean success = true;
    private int statusCode = 1;

    public CustomSuccessResponse(T data) {
        this.data = data;
    }

    public CustomSuccessResponse(T codes, int statusCode, boolean success) {
        this.codes = codes;
        this.statusCode = statusCode;
        this.success = success;
    }
}
