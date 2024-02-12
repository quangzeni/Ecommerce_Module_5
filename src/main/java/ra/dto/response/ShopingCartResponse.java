package ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.model.Product;
import ra.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopingCartResponse {
    private int id;
    private Product product;
    private int orderQuantity;
}
