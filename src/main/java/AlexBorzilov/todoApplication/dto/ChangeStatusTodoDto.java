package AlexBorzilov.todoApplication.dto;

import AlexBorzilov.todoApplication.error.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangeStatusTodoDto {
    @NotNull(message = ValidationConstants.TODO_STATUS_NOT_NULL)
    private Boolean status;
}
