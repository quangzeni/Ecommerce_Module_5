package ra.dto.response;

import lombok.*;
import ra.model.Product;
import ra.model.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShopingCartResponse {
    private int id;
    private Product product;
    private int orderQuantity;
}
