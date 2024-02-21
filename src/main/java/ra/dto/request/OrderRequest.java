package ra.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import ra.model.EOrder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    private EOrder status;
}
