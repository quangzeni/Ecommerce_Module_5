package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.ShopingCart;

public interface ShopingCartRepository extends JpaRepository<ShopingCart,Integer> {
}
