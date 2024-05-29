package security.instagram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.instagram.controller.mapper.UserMapper;
import security.instagram.dto.UserDto;
import security.instagram.dto.UserProfile;
import security.instagram.entity.Role;
import security.instagram.entity.User;
import security.instagram.repository.UserRepository;
import security.instagram.utils.exception.InvalidRequestException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserMapper userMapper;


    public UserDto createNewUser(UserDto request) {
        if (request.getEmail() == null
                || request.getEmail().equals("")
                || checkExistingEmail(request.getEmail(), request.getUsername())
        ) {
            throw new InvalidRequestException(LocalDateTime.now(), "ConstantMessages.INVALID_REQUEST",
                    String.format("ConstantMessages.INVALID_USERNAME", request.getUsername()), HttpStatus.BAD_REQUEST);
        }
        var user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .enabled(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        request.setUserId(savedUser.getId());
        request.setPassword(null);
        request.setConfirmPassword(null);
        return request;
    }

    @Override
    public UserProfile getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserProfile userProfile = userMapper.showProfile(user.get());
        return userProfile;
    }
    private boolean checkExistingEmail(String email, String username) {
        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username);
    }
    private static boolean isNull(Object o){
        return Objects.isNull(o);
    }
}

