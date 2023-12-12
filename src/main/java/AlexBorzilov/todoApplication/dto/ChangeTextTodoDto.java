package AlexBorzilov.todoApplication.dto;

import AlexBorzilov.todoApplication.error.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangeTextTodoDto {
    @NotBlank(message = ValidationConstants.TODO_TEXT_SIZE_NOT_VALID)
    @NotNull(message = ValidationConstants.TODO_TEXT_SIZE_NOT_VALID)
    @Size(min = 3, max = 160, message = ValidationConstants.TODO_TEXT_SIZE_NOT_VALID)
    private String text;
}
