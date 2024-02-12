package ra.service;

import org.springframework.stereotype.Service;
import ra.dto.request.CategoryRequest;
import ra.dto.response.CategoryResponse;

import java.util.List;
@Service
public interface CategoryService {
    List<CategoryResponse> findAll();

    List<CategoryResponse> findByDirectionAndPaging(String direction, String orderBy, int page, int size);

    CategoryResponse findById(Long id);

    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse update(CategoryRequest categoryRequest, Long id);

    boolean updateStatus(Long id, CategoryRequest categoryRequest);

    boolean existsCategoryByName (CategoryRequest categoryRequest);

}
