package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.ShopingCart;

import java.util.Optional;

public interface ShopingCartRepository extends JpaRepository<ShopingCart,Integer> {
    Optional<ShopingCart> findByUserId(Integer id);
}
