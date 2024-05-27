package security.instagram.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import security.instagram.config.token.JwtResponse;
import security.instagram.dto.LoginRequest;
import security.instagram.dto.UserDto;
import security.instagram.service.AuthenticationService;
import security.instagram.service.UserServiceImpl;
import security.instagram.utils.Constants;
import security.instagram.utils.ForumMessage;
import security.instagram.utils.Validate;
import security.instagram.utils.exception.ForumException;
import security.instagram.utils.exception.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationService authenticationService;
    @PostMapping("/registry")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) throws InvalidRequestException {
        log.info(Constants.SEPARATE);
        if(Objects.isNull(userDto.getDateOfBirth()) || userDto.getDateOfBirth().equals("")){
            userDto.setDateOfBirth(null);
        }
        UserDto dto = userServiceImpl.createNewUser(userDto);

        log.info("Registry success");
        log.info(Constants.SEPARATE);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private String getJwtToken(HttpServletRequest request) {
        if (request.getHeader(Constants.AUTHORIZATION_HEADER) != null
                && request.getHeader(Constants.AUTHORIZATION_HEADER).startsWith(Constants.TOKEN_PREFIX)) {
            return request.getHeader(Constants.AUTHORIZATION_HEADER).replace(Constants.TOKEN_PREFIX, "");
        }
        return null;
    }

}
