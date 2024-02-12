package ra.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dto.request.CategoryRequest;
import ra.dto.response.CategoryResponse;
import ra.model.Category;
import ra.repository.CategoryRepository;
import ra.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> findByDirectionAndPaging(String direction, String orderBy, int page, int size) {
        Pageable pageable;
        if (direction.equals("ASC")) {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        }
        List<Category> categoryList = categoryRepository.findAll(pageable).getContent();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return modelMapper.map(category, CategoryResponse.class);
        }
        return null;
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category newCategory = categoryRepository.save(modelMapper.map(categoryRequest, Category.class));
        return modelMapper.map(newCategory, CategoryResponse.class);
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category exitstingCategory = categoryOptional.get();
            exitstingCategory.setName(categoryRequest.getName());
            exitstingCategory.setDescription(categoryRequest.getDescription());
            exitstingCategory.setStatus(categoryRequest.isStatus());
            Category updatedCategory = categoryRepository.save(exitstingCategory);
            return modelMapper.map(updatedCategory, CategoryResponse.class);
        }
        return null;
    }

    @Override
    public boolean updateStatus(Long id, CategoryRequest categoryRequest) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category categoryUpdate = categoryOptional.get();
            categoryUpdate.setStatus(categoryRequest.isStatus());
            categoryRepository.save(categoryUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existsCategoryByName(CategoryRequest categoryRequest) {
        return categoryRepository.existsCategoryByName(modelMapper.map(categoryRequest,Category.class).getName());
    }


}
