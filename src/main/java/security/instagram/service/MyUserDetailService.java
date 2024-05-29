package security.instagram.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.instagram.dto.MyUserDetails;
import security.instagram.entity.User;
import security.instagram.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Service
@Transactional
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(email);
        if(Objects.isNull(user)){
            return null;
        }

        Collection<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(user.getRole()));
        return new MyUserDetails(user.getId(), user.getRole(), user.getEmail(), user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
    }

    public UserDetails toMyUserDetailService(User user){
        Collection<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(user.getRole()));
        return new MyUserDetails(user.getId(), user.getRole(), user.getEmail(), user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
    }
}
