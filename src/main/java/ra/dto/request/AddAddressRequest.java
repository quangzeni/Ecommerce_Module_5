package ra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddAddressRequest {
    private String fullAddress;
    private String phone;
    private String receiveName;
}
