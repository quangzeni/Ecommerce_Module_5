package ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignInResponse {
    private String userName;
    private String email;
    private String fullName;
    private final String TYPE = "Bearer";
    private Collection<? extends GrantedAuthority> authorities;
    private String accessToken;
    private String refreshToken;
}
