package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ra.dto.request.ProductRequest;
import ra.dto.response.ProductResponse;
import ra.dto.response.ProductResponseAdminId;
import ra.model.Category;
import ra.model.Product;
import ra.repository.CategoryRepository;
import ra.repository.ProductRepository;
import ra.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> searchByNameOrDescription(String keyvalue) {
//        List<>
        return productRepository.findByNameOrDescriptionContainingIgnoreCase(keyvalue, keyvalue).stream().map(product ->
                modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

//    Lấy tất cả sản phẩm được bán status = true
    @Override
    public List<ProductResponse> findByDirectionAndPagingWithStatusTrue(Pageable pageable){
        Page<Product>productPage = productRepository.findByStatusTrue(pageable);
        return productPage.getContent().stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .collect(Collectors.toList());
    }

//    Danh sách sản phẩm nổi bật
    @Override
    public List<ProductResponse> getFeaturedProducts() {
        List<Product> productList = productRepository.findFeaturedProducts();
        return productList.stream().map(
                product -> modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

//    Danh sách sản phẩm bán chạy
    @Override
    public List<ProductResponse> findBestSellerProducts() {
        List<Product> productList = productRepository.findBestSellerProducts();
        return productList.stream().map(
                product -> modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findByDirectionAndPaging(Pageable pageable) {
        return productRepository.findAll(pageable).getContent().stream().map(product ->
                modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

    //Tạo mới
    @Override
    public ProductResponse save(ProductRequest productRequest) {
//        Product product = modelMapper.map(productRequest, Product.class);
        Product product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .status(productRequest.isStatus())
                .category(categoryRepository.getCategoryById(productRequest.getCategoryId()))
                .build();
        Product newProduct = productRepository.save(product);
//        Product product = productRepository.save(modelMapper.map(productRequest, Product.class));
        return modelMapper.map(newProduct, ProductResponse.class);
    }

    @Override
    public boolean existsProductByName(ProductRequest productRequest) {
        boolean exists = productRepository.existsByName(productRequest.getName());
        return exists;
    }

    @Override
    public List<ProductResponse> getTop3NewsProducts() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Product> productPage = productRepository.findAllByOrderByCreated(pageable);
        List<Product> products = productPage.getContent();
        return productPage.stream().map(product ->
                modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<ProductResponse>> getProducsByCategoryId(Long catalogId) {
        Optional<Category> categoryOpt = categoryRepository.findById(catalogId);
        Map<Long, List<ProductResponse>> map = new HashMap<>();
        if (categoryOpt.isPresent()) {
            List<Product> productList = productRepository.getProductsByCategory(categoryOpt.get());
            List<ProductResponse> productResponseList = productList.stream().map(product ->
                    modelMapper.map(product, ProductResponse.class)).collect(Collectors.toList());
            map.put(catalogId, productResponseList);
        }
        return map;
    }

    @Override
    public ProductResponse getProductsById(Long id) {
        Optional<Product> productOpt = productRepository.getProductsById(id);
        return productOpt.map(product -> modelMapper.map(product, ProductResponse.class)).orElse(null);
    }

    @Override
    public ProductResponseAdminId getProductsByIdWithAdmin(Long id) {
        Optional<Product> productOpt = productRepository.getProductsById(id);
        return productOpt.map(product -> modelMapper.map(product,ProductResponseAdminId.class))
                .orElse(null);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product exitstingProduct = productOptional.get();
            exitstingProduct.setName(productRequest.getName());
            exitstingProduct.setSku(productRequest.getSku());
            exitstingProduct.setDescription(productRequest.getDescription());
            exitstingProduct.setUnitPrice(productRequest.getUnitPrice());
            exitstingProduct.setStockQuantity(productRequest.getStockQuantity());
            exitstingProduct.setImage(productRequest.getImage());
            exitstingProduct.setStatus(productRequest.isStatus());
            exitstingProduct.setCategory(categoryRepository.getCategoryById(productRequest.getCategoryId()));

            Product newProduct = productRepository.save(exitstingProduct);
            return modelMapper.map(newProduct, ProductResponse.class);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Product> productOptional = productRepository.getProductsById(id);
        if (productOptional.isPresent()) {
            Product deleteProduct = productOptional.get();
            deleteProduct.setStatus(false);
            productRepository.save(deleteProduct);
            return true;
        }
        return false;
    }

//    @Override
//    public Map<Long, Long> findTopProdductsInWishList() {
//        Map<Long, Long> map = productRepository.findTopProdductsInWishList();
//        return map;
//    }


}
