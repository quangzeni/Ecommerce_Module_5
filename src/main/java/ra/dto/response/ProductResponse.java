package ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.model.Category;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private int stockQuantity;
    private String image;
    private boolean status;
    private Long categoryId;
}
