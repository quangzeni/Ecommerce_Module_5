package ra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_number", columnDefinition = "varchar(100)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String serialNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "order_at")
    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date orderAt;
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EOrder status;
    @Column(columnDefinition = "varchar(100)")
    private String note;
    @Column(name = "receive_name", columnDefinition = "varchar(100)")
    private String receiveName;
    @Column(name = "receive_address", columnDefinition = "varchar(100)")
    private String receiveAddress;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @Formula("(created_at + interval 4 day)") // tự động cộng thêm 4 ngày cho ngày giao hàng dự kiến
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    @Column(name = "receive_at")
    private Date receivedAt;
}
