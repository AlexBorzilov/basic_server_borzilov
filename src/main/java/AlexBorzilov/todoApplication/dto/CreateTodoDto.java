package AlexBorzilov.todoApplication.dto;

import AlexBorzilov.todoApplication.error.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateTodoDto {
    @Size(min = 3, max = 160, message = ValidationConstants.TODO_TEXT_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.TODO_TEXT_NOT_NULL)
    @NotNull(message = ValidationConstants.TODO_TEXT_NOT_NULL)
    private String text;
}
