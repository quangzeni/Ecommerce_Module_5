package ra.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddAddressResponse {
    private String fullAddress;
    private String phone;
    private String receiveName;
}
