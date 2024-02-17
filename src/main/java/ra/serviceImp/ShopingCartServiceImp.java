package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.dto.request.AddProductRequest;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.Message;
import ra.dto.response.ProductResponse;
import ra.dto.response.ShopingCartResponse;
import ra.model.Product;
import ra.model.ShopingCart;
import ra.model.User;
import ra.repository.ProductRepository;
import ra.repository.ShopingCartRepository;
import ra.repository.UserRepository;
import ra.service.ShopingCartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopingCartServiceImp implements ShopingCartService {
    @Autowired
    private ShopingCartRepository shopingCartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ShopingCartResponse save(ShopingCartRequest shopingCartRequest) {
        ShopingCart shopingCart = ShopingCart.builder()
                .user(userRepository.getUserById(shopingCartRequest.getUserId()))

                .build();

        ShopingCart newShopingCart = shopingCartRepository.save(shopingCart);
        return modelMapper.map(newShopingCart, ShopingCartResponse.class);
    }

    //    Danh sách sản phẩm trong giỏ hàng
    @Override
    public List<ProductResponse> getProductById(Long userId) {
        List<ShopingCart> shopingCartList = shopingCartRepository.findAllByUserId(userId);
        if (!shopingCartList.isEmpty()) {
            List<Product> productList = new ArrayList<>();
            for (ShopingCart shopingCart : shopingCartList) {
                Product product1 = shopingCart.getProduct();
                if (product1 != null) {
                    productList.add(product1);
                }
            }
            return productList.stream().map(product ->
                    modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
        } else {
            throw new RuntimeException("user has no ShopingCart");
        }
    }

    //    Thêm mới sản phẩm vào giỏ hàng (payload: productId and quantity)
    @Override
    public ShopingCartResponse addProduct(Long userId, AddProductRequest addProductRequest) {
        // Lấy thông tin sản phẩm từ request
        Long productId = addProductRequest.getProductId();
        int quantity = addProductRequest.getQuantity();

        ShopingCart shopingCart = shopingCartRepository.findByUserIdAndProductId(userId, productId);
        if (shopingCart == null) {
//            Tạo giỏ hàng mơ và thêm sản phẩm
            shopingCart = new ShopingCart();
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found"));
            shopingCart.setUser(user);
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found"));
            shopingCart.setProduct(product);
            shopingCart.setOrderQuantity(quantity);
        } else {
            shopingCart.setOrderQuantity(shopingCart.getOrderQuantity() + quantity);
        }

        shopingCart = shopingCartRepository.save(shopingCart);
        return modelMapper.map(shopingCart, ShopingCartResponse.class);

    }

    @Override
    public ShopingCartResponse updateQuantity(Long userId, Integer shopingCartId, AddProductRequest
            addProductRequest){
        ShopingCart shopingCart = shopingCartRepository.findById(shopingCartId).orElseThrow(()->
                new RuntimeException("ShopingCart not found"));
        if (!shopingCart.getUser().getId().equals(userId)){
            throw new UsernameNotFoundException("User and ShopingCart not match");
        }
        shopingCart.setOrderQuantity(addProductRequest.getQuantity());

        ShopingCart shopingCart1 = shopingCartRepository.save(shopingCart);

//        return modelMapper.map(shopingCart1,ShopingCartResponse.class);
        return ShopingCartResponse.builder()
                .id(shopingCart1.getId())
                .product(shopingCart1.getProduct())
                .orderQuantity(shopingCart1.getOrderQuantity())
                .build();
    }

    @Override
    public void delete(Long userId, Integer shopingCartId) {
        ShopingCart shopingCart = shopingCartRepository.findById(shopingCartId).orElseThrow(()->
                new UsernameNotFoundException("ShopingCart not exist"));
        if (!shopingCart.getUser().getId().equals(userId)){
            throw new RuntimeException("UserId and ShopingCart not match");
        }
        shopingCartRepository.delete(shopingCart);

    }

    @Override
    public void  deleteAll(Long userId){
        List<ShopingCart> shopingCartList = shopingCartRepository.findAllByUserId(userId);
        for (ShopingCart shopingCart : shopingCartList){
            shopingCartRepository.delete(shopingCart);
        }
    }

    @Override
    public void checkout(Long userId) {
        List<ShopingCart> shopingCartList = shopingCartRepository.findAllByUserId(userId);

        // Kiểm tra xem có sản phẩm trong giỏ hàng không
        if (shopingCartList.isEmpty()) {
            throw new RuntimeException("No items in the shopping cart to checkout");
        }

        // Thực hiện quá trình đặt hàng cho từng sản phẩm trong giỏ hàng
        for (ShopingCart shopingCart : shopingCartList) {
            // Thực hiện các bước đặt hàng, ví dụ: tạo đơn hàng mới, xử lý thanh toán, cập nhật trạng thái sản phẩm, v.v.
            // Đây chỉ là ví dụ, bạn cần thay đổi theo logic kinh doanh cụ thể của bạn
            // ...

            // Xóa sản phẩm khỏi giỏ hàng sau khi đã đặt hàng thành công
            shopingCartRepository.delete(shopingCart);
        }
    }


}
