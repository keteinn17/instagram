package security.instagram.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.instagram.dto.UserDto;
import security.instagram.entity.Decentralization;
import security.instagram.service.UserServiceImpl;
import security.instagram.service.DecentralizationService;
import security.instagram.utils.Constants;
import security.instagram.utils.exception.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.logging.Logger;

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
    private final DecentralizationService decentralizationService;
    private final Logger logger = Logger.getLogger(AuthController.class.getName());
    @PostMapping("/registry")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) throws InvalidRequestException {
        logger.info(Constants.SEPARATE);
        if(Objects.isNull(userDto.getDateOfBirth()) || userDto.getDateOfBirth().isEmpty()){
            userDto.setDateOfBirth(null);
        }
        UserDto dto = userServiceImpl.createNewUser(userDto);

        logger.info("Registry success");
        logger.info(Constants.SEPARATE);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Add decentralization rule
    @PostMapping("/decentralization/add")
    public ResponseEntity<?> addDecentralization(@RequestBody Decentralization decentralization) {
        Decentralization created = decentralizationService.addDecentralization(decentralization);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Update decentralization rule
    @PostMapping("/decentralization/update")
    public ResponseEntity<?> updateDecentralization(@RequestBody Decentralization decentralization) {
        Decentralization updated = decentralizationService.updateDecentralization(decentralization);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete decentralization rule
    @PostMapping("/decentralization/delete")
    public ResponseEntity<?> deleteDecentralization(@RequestBody Long id) {
        decentralizationService.deleteDecentralization(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String getJwtToken(HttpServletRequest request) {
        if (request.getHeader(Constants.AUTHORIZATION_HEADER) != null
                && request.getHeader(Constants.AUTHORIZATION_HEADER).startsWith(Constants.TOKEN_PREFIX)) {
            return request.getHeader(Constants.AUTHORIZATION_HEADER).replace(Constants.TOKEN_PREFIX, "");
        }
        return null;
    }

}
