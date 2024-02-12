package ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpResponse {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private List<String> listRoles;
}
