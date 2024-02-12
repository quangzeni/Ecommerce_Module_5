package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.CategoryRequest;
import ra.dto.response.CategoryResponse;
import ra.dto.response.Message;
import ra.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api.myservice.com/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/catalogs")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> listCategory = categoryService.findAll();
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }

    @GetMapping("admin/categories")
    public ResponseEntity<List<CategoryResponse>> findByDirectionAndPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "name") String orderBy
    ) {
        List<CategoryResponse> listCategory = categoryService.findByDirectionAndPaging(direction, orderBy, page, size);
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }

    @GetMapping("admin/categories/{categoryId}")
    public ResponseEntity<?> findById(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        if (categoryResponse == null) {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        }
    }

    @PostMapping("admin/categories")
    public ResponseEntity<?> save(@RequestBody CategoryRequest categoryRequest) {
        boolean exitsName = categoryService.existsCategoryByName(categoryRequest);
        if (exitsName){
            return new ResponseEntity<>(new Message("CategoryName is exits! Choose another name"), HttpStatus.OK);
        }else {
            CategoryResponse categoryResponse = categoryService.save(categoryRequest);
            return new ResponseEntity<>(categoryResponse,HttpStatus.CREATED);
        }
    }

    @PutMapping("admin/categories/{categoryId}")
    public ResponseEntity<?> update(@RequestBody CategoryRequest categoryRequest,
                                    @PathVariable Long categoryId){
        CategoryResponse categoryResponse = categoryService.update(categoryRequest,categoryId);
        if (categoryResponse == null){
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>( categoryResponse,HttpStatus.OK);
        }
    }

    @PatchMapping("admin/categories/{categoryId}")
    public ResponseEntity<Message> updateStatus(@PathVariable Long categoryId,
                                                @RequestBody CategoryRequest categoryRequest){
        boolean result = categoryService.updateStatus(categoryId,categoryRequest);
        if (result){
            return new ResponseEntity<>(new Message("Status Updated"), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Message("Id not found"), HttpStatus.NOT_FOUND);
        }
    }
}
