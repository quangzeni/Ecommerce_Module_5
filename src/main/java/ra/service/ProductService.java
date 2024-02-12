package ra.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.dto.request.ProductRequest;
import ra.dto.response.ProductResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface ProductService {
    List<ProductResponse> searchByNameOrDescription(String name, String description);
    List<ProductResponse> findByDirectionAndPaging(Pageable pageable);
    ProductResponse save(ProductRequest productRequest);
    boolean existsProductByName(ProductRequest productRequest);
    List<ProductResponse> getTop3NewsProducts();
    Map<Long,List<ProductResponse>> getProducsByCategoryId(Long catalogId);
    ProductResponse getProductsById(Long id);
    ProductResponse update(ProductRequest productRequest,Long id);
    boolean delete(Long id);
//    Map<Long, Long> findTopProdductsInWishList();
}
