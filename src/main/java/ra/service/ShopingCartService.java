package ra.service;

import org.springframework.stereotype.Service;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.ShopingCartResponse;

@Service
public interface ShopingCartService {
    ShopingCartResponse save(ShopingCartRequest shopingCartRequest);
}
