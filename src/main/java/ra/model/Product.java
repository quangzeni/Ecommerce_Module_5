package ra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sku;
    @Column(name = "product_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
    @Column(name = "image", columnDefinition = "varchar(255)")
    private String image;
    @Column(name = "product_status", columnDefinition = "bit default 1")
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    @Column(name = "created_at")
    private Date created;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated;

}
