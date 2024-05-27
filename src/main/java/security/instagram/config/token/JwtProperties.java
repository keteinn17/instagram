package security.instagram.config.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ket_ein17
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private String secretKey;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
}
