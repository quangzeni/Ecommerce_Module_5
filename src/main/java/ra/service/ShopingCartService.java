package ra.service;

import org.springframework.stereotype.Service;
import ra.dto.request.AddProductRequest;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.ShopingCartResponse;
import ra.model.Product;

import java.util.List;

@Service
public interface ShopingCartService {
    ShopingCartResponse save(ShopingCartRequest shopingCartRequest);
    List<Product> getProductById(Integer userId);
    ShopingCartResponse addProduct(Integer userId, AddProductRequest addProductRequest);
}
