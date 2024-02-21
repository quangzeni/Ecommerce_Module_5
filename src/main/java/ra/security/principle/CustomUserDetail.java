package ra.security.principle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.User;

import java.util.Collection;

@Getter
@Setter
@Builder
public class CustomUserDetail implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //    @Override
//    public boolean isEnabled() {
//        return user.isStatus();
//    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
