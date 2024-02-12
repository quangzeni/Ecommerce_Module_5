package ra.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {
//    private Long id;
    @NotNull(message = "Category name cannot be empty")
    @Length(max = 100,message = "Maximum category name length is 100 characters")
    private String name;
    private String description;
    private boolean status;
}
