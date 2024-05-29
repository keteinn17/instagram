package security.instagram.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import security.instagram.entity.Role;

import java.util.Collection;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
public class MyUserDetails extends User {
    private final Long userID;
    private final Role role;
    private final String email;
    private final String username;

    public MyUserDetails(Long userID, Role role, String email,String username, String password, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(email, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email=email;
        this.userID = userID;
        this.role = role;
        this.username = username;
    }

    @Override
    public String getUsername(){
        return username;
    }
}
