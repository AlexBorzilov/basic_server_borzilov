package AlexBorzilov.todoApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateTodoDto {
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 150, message = "text should be between 3 and 150")
    private String text;
}
