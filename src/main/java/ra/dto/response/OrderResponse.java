package ra.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import ra.model.EOrder;
import ra.model.User;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {
    private String serialNumber;

    private Long userId;

    private Date orderAt;

    private BigDecimal totalPrice;

    private EOrder status;

    private String note;

    private String receiveName;

    private String receiveAddress;

    private Date createdAt;

    private Date receivedAt;
}
