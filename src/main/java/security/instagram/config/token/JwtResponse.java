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
public class JwtResponse implements Serializable {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class User implements Serializable {
        private String firstName;
        private String lastName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Data implements Serializable {
        private String refreshToken;
        private String accessToken;
        private User user;
    }

    private Data data;

    private String userId;
    private String username;
    private String email;
    private String role;
    private String token_type;
    private String access_token;
    private String refresh_token;
    @JsonSerialize(using = DateToSecondSerializer.class)
    private Date exp;

    public JwtResponse(String userId,
                       String username,
                       String email,
                       String role,
                       String token_type,
                       String access_token,
                       String refresh_token,
                       Date exp) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.token_type = token_type;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.exp = exp;
        // Map username -> firstName and email -> lastName (adjust if you have real first/last names)
        this.data = new Data(refresh_token, access_token, new User(username, email));
    }
}
