package AlexBorzilov.todoApplication.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangeStatusTodoDto {
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    Boolean status;
}
