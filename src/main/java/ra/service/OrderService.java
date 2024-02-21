package ra.service;

import org.springframework.stereotype.Service;
import ra.dto.request.OrderRequest;
import ra.dto.response.OrderResponse;
import ra.model.EOrder;

import java.util.List;

@Service
public interface OrderService {
    List<OrderResponse> findAll();
    List<OrderResponse> findByStatus(EOrder orderStatus);
    OrderResponse updateStatus(Long orderId, OrderRequest orderRequest);
    OrderResponse orderDetail(Long orderId);
}
