package ra.service;

import org.springframework.stereotype.Service;
import ra.dto.request.AddProductRequest;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.ProductResponse;
import ra.dto.response.ShopingCartResponse;
import ra.model.Product;

import java.util.List;

@Service
public interface ShopingCartService {
    ShopingCartResponse save(ShopingCartRequest shopingCartRequest);
    List<ProductResponse> getProductById(Long userId);
    ShopingCartResponse addProduct(Long userId, AddProductRequest addProductRequest);
    ShopingCartResponse updateQuantity(Long userId,Integer shopingCartId, AddProductRequest addProductRequest);
    void delete(Long userId, Integer shopingCartId);
    void deleteAll(Long userId);
    void checkout(Long userId);
}
