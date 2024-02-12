package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.ShopingCartResponse;
import ra.model.ShopingCart;
import ra.repository.ShopingCartRepository;
import ra.repository.UserRepository;
import ra.service.ShopingCartService;
@Service
public class ShopingCartServiceImp implements ShopingCartService {
    @Autowired
    private ShopingCartRepository shopingCartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ShopingCartResponse save(ShopingCartRequest shopingCartRequest) {
        ShopingCart shopingCart = ShopingCart.builder()
                .user(userRepository.getUserById(shopingCartRequest.getUserId()))

                .build();

        ShopingCart newShopingCart = shopingCartRepository.save(shopingCart);
        return modelMapper.map(newShopingCart,ShopingCartResponse.class);
    }
}
