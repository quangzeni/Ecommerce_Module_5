package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.dto.request.AddProductRequest;
import ra.dto.request.ShopingCartRequest;
import ra.dto.response.Message;
import ra.dto.response.ShopingCartResponse;
import ra.model.Product;
import ra.model.ShopingCart;
import ra.repository.ProductRepository;
import ra.repository.ShopingCartRepository;
import ra.repository.UserRepository;
import ra.service.ShopingCartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Product> getProductById(Integer userId) {
        Optional<ShopingCart> shopingCartOptional = shopingCartRepository.findByUserId(userId);
        if (shopingCartOptional.isPresent()) {
            ShopingCart shopingCart = shopingCartOptional.get();
            List<Product> productList = shopingCart.getListProduct();
            return productList;
        } else {
            throw new RuntimeException("user has no ShopingCart");
        }
    }

    //    Thêm mới sản phẩm vào giỏ hàng (payload: productId and quantity)
    @Override
    public ShopingCartResponse addProduct(Integer userId, AddProductRequest addProductRequest) {
        // Kiểm tra và lấy thông tin giỏ hàng từ repository
        ShopingCart shopingCart = shopingCartRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not found Cart with userId"));

        // Lấy danh sách sản phẩm hiện tại trong giỏ hàng
        List<Product> productList = shopingCart.getListProduct();

        // Lấy thông tin sản phẩm từ request
        Long productId = addProductRequest.getProductId();
        int quantity = addProductRequest.getQuantity();

        // Kiểm tra và lấy thông tin sản phẩm từ repository
        Product newProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found"));

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<Product> existingProductOptional = productList.stream()
                .filter(product -> product.equals(newProduct)).findFirst();

        // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
        if (existingProductOptional.isPresent()){
            shopingCart.setOrderQuantity(shopingCart.getOrderQuantity()+quantity);
        }else {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm vào danh sách
            productList.add(newProduct);
            shopingCart.setOrderQuantity(quantity);
        }
        // Lưu giỏ hàng đã cập nhật vào cơ sở dữ liệu
        ShopingCart shopingCart1 = shopingCartRepository.save(shopingCart);

        return modelMapper.map(shopingCart1,ShopingCartResponse.class);
    }


}
