package AlexBorzilov.todoApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetNewsDto<T> {
    private T content;
    private long notReady;
    private long numberOfElements;
    private long ready;

    public GetNewsDto(T content, long notReady, long numberOfElements, long ready) {
        this.content = content;
        this.notReady = notReady;
        this.numberOfElements = numberOfElements;
        this.ready = ready;
    }
}
