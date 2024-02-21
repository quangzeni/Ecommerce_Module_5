package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dto.request.OrderRequest;
import ra.dto.response.OrderResponse;
import ra.model.EOrder;
import ra.model.Order;
import ra.repository.OrderRepository;
import ra.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    //    Danh sách tất cả đơn hàng
    @Override
    public List<OrderResponse> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList
                .stream().map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    //    Danh sách đơn hàng theo trạng thái
    @Override
    public List<OrderResponse> findByStatus(EOrder orderStatus) {
        List<Order> orderList = orderRepository.findByStatus(orderStatus);
        return orderList
                .stream().map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    //    Cập nhật trạng thái đơn hàng (payload : orderStatus)
    @Override
    public OrderResponse updateStatus(Long orderId, OrderRequest orderRequest) {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(()->
                new RuntimeException("Order not exist"));
        EOrder newStatus = orderRequest.getStatus();
        orderExist.setStatus(newStatus);
        orderRepository.save(orderExist);
        return modelMapper.map(orderExist,OrderResponse.class);
    }

    //    Chi tiết đơn hàng
    @Override
    public OrderResponse orderDetail(Long orderId) {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(()->
                new RuntimeException("Order not exist"));
        return modelMapper.map(orderExist,OrderResponse.class);
    }
}
