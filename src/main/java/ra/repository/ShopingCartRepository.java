package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.Product;
import ra.model.ShopingCart;
import ra.model.User;

import java.util.List;
import java.util.Optional;

public interface ShopingCartRepository extends JpaRepository<ShopingCart,Integer> {
    Optional<ShopingCart> findByUserId(Long id);
    Optional<ShopingCart> findByProduct(Product product);
    List<ShopingCart> findAllByUserId(Long userId);
    ShopingCart findByUserIdAndProductId(Long userId,Long productId);
}
