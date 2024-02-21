package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.OrderRequest;
import ra.dto.response.Message;
import ra.dto.response.OrderResponse;
import ra.model.EOrder;
import ra.service.OrderService;
import ra.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api.myservice.com/v1/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

//    Danh sách tất cả đơn hàng
    @GetMapping()
    public ResponseEntity<List<OrderResponse>> findAll() {
        List<OrderResponse> orderResponseList = orderService.findAll();
        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
    }

//    Danh sách đơn hàng theo trạng thái
    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<?> getAllWithStatus(@PathVariable EOrder orderStatus) {
        List<OrderResponse> orderResponseList = orderService.findByStatus(orderStatus);
        if (orderResponseList.isEmpty()) {
            return new ResponseEntity<>(new Message("Empty Order With status " + orderStatus), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
        }
    }

//    Cập nhật trạng thái đơn hàng (payload : orderStatus)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest){
        try {
            OrderResponse orderResponse = orderService.updateStatus(orderId,orderRequest);
            return new ResponseEntity<>(orderResponse,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new Message("It has not any Order with orderId: " + orderId),HttpStatus.NOT_FOUND);
        }
    }

//    Chi tiết đơn hàng
    @GetMapping("/{orderId}")
    public ResponseEntity<?> orderDetail(@PathVariable Long orderId){
        try {
            OrderResponse orderResponse = orderService.orderDetail(orderId);
            return new ResponseEntity<>(orderResponse,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new Message("It has not any Order with orderId: " + orderId),HttpStatus.NOT_FOUND);
        }
    }
}
