package security.instagram.service;

import security.instagram.dto.UserDto;
import security.instagram.dto.UserProfile;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public interface UserService {
    UserDto createNewUser(UserDto userDto) throws Exception;

    UserProfile getUserById(Long id);

}