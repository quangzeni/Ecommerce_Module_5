package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.ProductResponse;
import ra.dto.response.ShopingCartResponse;
import ra.service.ShopingCartService;

@RestController
@RequestMapping("/api.myservice.com/v1/shoping-cart")
public class ShopingCartController {
    @Autowired
    private ShopingCartService shopingCartService;

    @PostMapping()
    public ResponseEntity<ShopingCartResponse> save(@RequestBody ShopingCartRequest shopingCartRequest){
        ShopingCartResponse shopingCartResponse = shopingCartService.save(shopingCartRequest);
        return new ResponseEntity<>(shopingCartResponse, HttpStatus.OK);
    }
}
