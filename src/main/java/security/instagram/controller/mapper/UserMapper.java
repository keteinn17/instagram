package security.instagram.controller.mapper;

import org.springframework.stereotype.Component;
import security.instagram.dto.UserDto;
import security.instagram.dto.UserProfile;
import security.instagram.entity.User;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        //Optional<Role> role = roleRepository.findById(user.getRoleId());
        userDto.setRole(user.getRole());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
//        userDto.setGender(user.getGender());
        return userDto;
    }

    public UserProfile showProfile(User user){
//        UserAdditionalInfo userAdditionalInfo = user.getInfo();
        UserProfile userProfile = UserProfile.builder()
                .email(user.getEmail())
                .username(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
//                .gender(user.getGender())
//                .accountType(user.getAccountType())
                .avatarId(user.getAvatarId())
//                .userAdditionalInfoProfile(userAdditionalInfoMapper.toUserAdditionalDto(userAdditionalInfo))
                .build();
        return userProfile;
    }
}
