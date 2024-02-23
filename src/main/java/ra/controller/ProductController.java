package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ProductRequest;
import ra.dto.response.Message;
import ra.dto.response.ProductResponse;
import ra.dto.response.ProductResponseAdminId;
import ra.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api.myservice.com/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/search")
    public ResponseEntity<?> searchByNameOrDescription(@RequestParam(required = false) String keyValue) {
        List<ProductResponse> productResponseList = productService.searchByNameOrDescription(keyValue);
        if (productResponseList == null) {
            return new ResponseEntity<>(new Message("Has no Name or description contain seach"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productResponseList, HttpStatus.OK);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findByDirectionAndPagingWithStatusTrue(@PageableDefault(size = 2, page = 0,
            sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        List<ProductResponse> productResponseList = productService.findByDirectionAndPagingWithStatusTrue(pageable);
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }


    // Danh sách sản phẩm mới

    @GetMapping("/products/new-products")
    public ResponseEntity<List<ProductResponse>> getTopNewsProducts() {
        List<ProductResponse> productResponseList = productService.getTop3NewsProducts();
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    //    Danh sách sản phẩm theo danh mục
    @GetMapping("/products/catalogs/{catalogId}")
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable Long catalogId) {
        Map<Long, List<ProductResponse>> map = productService.getProducsByCategoryId(catalogId);
//        Kiểm tra và loại bỏ map có đối tượng rỗng.
        map.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        if (map.isEmpty()) {
            return new ResponseEntity<>(new Message("this CatalogId has not any Product"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //    Danh sách sản phẩm theo id
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductsById(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProductsById(productId);
        if (productResponse == null) {
            return new ResponseEntity<>(new Message("Has no product"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
    }
//    ADMIN API

    //    Lấy về danh sách sản phẩm, sắp xếp phân trang
    @GetMapping("/admin/products")
    public ResponseEntity<?> getAll(@PageableDefault(size = 3, page = 0
            , sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        List<ProductResponse> productResponseList = productService.findByDirectionAndPaging(pageable);
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    //    Lấy thông tin theo id
    @GetMapping("/admin/products/{productId}")
    public ResponseEntity<?> getProductsByIdWithAdminId(@PathVariable Long productId) {
        ProductResponseAdminId productResponseAdminId = productService.getProductsByIdWithAdmin(productId);
        if (productResponseAdminId == null) {
            return new ResponseEntity<>(new Message("Has no product"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productResponseAdminId, HttpStatus.OK);
        }
    }

    //    Thêm mới sản phẩm
    @PostMapping("/admin/products")
    public ResponseEntity<?> save(@RequestBody ProductRequest productRequest) {
        boolean exitsProduct = productService.existsProductByName(productRequest);
        if (exitsProduct) {
            return new ResponseEntity<>(new Message("ProductName is exits, please choose another name"), HttpStatus.OK);
        } else {
            ProductResponse productResponse = productService.save(productRequest);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        }
    }

    //    Chiỉnh sửa thông tin sản phẩm
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<?> update(@RequestBody ProductRequest productRequest,
                                    @PathVariable Long productId) {
        ProductResponse newProducResponse = productService.update(productRequest, productId);
        if (newProducResponse == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(newProducResponse, HttpStatus.OK);
        }
    }

    // Xóa sản phẩm
    @PatchMapping("/admin/products/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId) {
        boolean result = productService.delete(productId);
        if (result) {
            return new ResponseEntity<>(new Message("Deleted!(Thực ra là đổi status)"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }

    //    Danh sách sản phẩm nổi bật
    @GetMapping("/products/featured-products")
    public ResponseEntity<?> findTopProdductsInWishList() {
        List<ProductResponse> productResponseList = productService.getFeaturedProducts();
        if (productResponseList.isEmpty()){
            return new ResponseEntity<>(new Message("No Featured Product"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(productResponseList, HttpStatus.OK);
        }
    }

    //    Danh sách sản phẩm bán chạy
    @GetMapping("/products/best-seller-products")
    public ResponseEntity<?> findBestSellerProducts() {
        List<ProductResponse> productResponseList = productService.findBestSellerProducts();
        if (productResponseList.isEmpty()){
            return new ResponseEntity<>(new Message("No Best Seller Product"),HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(productResponseList, HttpStatus.OK);
        }

    }

}
