package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.AddProductRequest;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.Message;
import ra.dto.response.ProductResponse;
import ra.dto.response.ShopingCartResponse;
import ra.model.Product;
import ra.service.ShopingCartService;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/shoping-cart")
public class ShopingCartController {
    @Autowired
    private ShopingCartService shopingCartService;

    //Tạo mới giỏ hàng (payload : userId)
    @PostMapping()
    public ResponseEntity<ShopingCartResponse> save(@RequestBody ShopingCartRequest shopingCartRequest) {
        ShopingCartResponse shopingCartResponse = shopingCartService.save(shopingCartRequest);
        return new ResponseEntity<>(shopingCartResponse, HttpStatus.OK);
    }

//    Danh sách sản phẩm trong giỏ hàng
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer userId){
        try {
            List<Product> productList = shopingCartService.getProductById(userId);
            if (productList.isEmpty()){
                return new ResponseEntity<>(new Message("ShopingCart is empty"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(productList,HttpStatus.OK);
            }
        }catch (RuntimeException ex){
            return new ResponseEntity<>(new Message("UserId has not Shoping Cart"),HttpStatus.NOT_FOUND);
        }
    }

//    Thêm mới sản phẩm vào giỏ hàng (payload: productId and quantity)
    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addProduct(@PathVariable Integer userId, @RequestBody AddProductRequest addProductRequest){
        try {
            ShopingCartResponse shopingCartResponse = shopingCartService.addProduct(userId,addProductRequest);
            if (shopingCartResponse == null){
                return new ResponseEntity<>(new Message("Add product is fail"),HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(shopingCartResponse,HttpStatus.OK);
            }
        }catch (UsernameNotFoundException ex){
            return new ResponseEntity<>(new Message("Not found Cart with userId"),HttpStatus.NOT_FOUND);
        }catch (RuntimeException ex){
            return new ResponseEntity<>(new Message("ProductId not found"),HttpStatus.NOT_FOUND);
        }
    }

//    Thay đổi số lượng đặt hàng
}
