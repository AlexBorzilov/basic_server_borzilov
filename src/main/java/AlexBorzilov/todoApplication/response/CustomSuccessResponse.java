package AlexBorzilov.todoApplication.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSuccessResponse<T> {
    private T data;
    private T codes;
    private boolean status = true;
    private int statusCode = 1;

    public CustomSuccessResponse(T data) {
        this.data = data;
    }

    public CustomSuccessResponse(T codes, int statusCode) {
        this.codes = codes;
        this.statusCode = statusCode;
    }

    public CustomSuccessResponse(T codes, int statusCode, boolean status) {
        this.codes = codes;
        this.statusCode = statusCode;
        this.status = status;
    }
}
