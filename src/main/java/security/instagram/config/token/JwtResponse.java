package security.instagram.config.token;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import security.instagram.config.jackson.DateToSecondSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private String userId;
    private String username;
    private String email;
    private String role;
    private String token_type;
    private String access_token;
    private String refresh_token;
    @JsonSerialize(using = DateToSecondSerializer.class)
    private Date exp;
}
