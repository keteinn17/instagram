package security.instagram.config.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@Setter
public class BearerContext implements Serializable {
    private String role;
    private String userId;
    private String email;
    private String bearerToken;
//    private String accountType;
}
