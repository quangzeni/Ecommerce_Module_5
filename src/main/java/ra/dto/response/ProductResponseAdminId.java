package ra.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ra.model.Category;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponseAdminId {
    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private int stockQuantity;

    private String image;

    private boolean status;

    private Long categoryId;

    private Date created;

    private Date updated;
}
