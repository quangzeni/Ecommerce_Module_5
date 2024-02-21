package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.Category;
import ra.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameOrDescriptionContainingIgnoreCase(String name, String description);
    Optional<Product> findById(Long id);
    boolean existsByName(String name);
    boolean existsById(Long id);
    Page<Product> findAllByOrderByCreated(Pageable pageable);
    Optional<Product> getProductsById(Long id);
    List<Product> getProductsByCategory(Category category);
    Page<Product> findByStatusTrue(Pageable pageable);
    @Query("SELECT w.product FROM WishList w GROUP BY w.product ORDER BY COUNT(w.product) DESC")
    List<Product> findFeaturedProducts();
    @Query("SELECT o.product from OrderDetails o GROUP BY o.product order by COUNT (o.product) DESC ")
    List<Product> findBestSellerProducts();

}
