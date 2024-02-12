package ra.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ra.model.Category;
import ra.model.Product;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {
    @Length(max = 100, message = "Maximum product name length is 100 characters")
    @NotNull(message = "Product name cannot be empty")
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private int stockQuantity;
    private String image;
    private boolean status;
    private Long categoryId;
}
