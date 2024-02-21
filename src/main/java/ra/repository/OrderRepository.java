package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.dto.response.OrderResponse;
import ra.model.EOrder;
import ra.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByStatus(EOrder orderstatus);
}
