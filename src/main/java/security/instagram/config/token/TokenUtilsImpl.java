package security.instagram.config.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import security.instagram.dto.MyUserDetails;
import security.instagram.entity.UserRefreshToken;
import security.instagram.service.UserRefreshTokenServiceImpl;
import security.instagram.utils.Constants;
import security.instagram.utils.exception.InvalidRequestException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ket_ein17
 */
@Component
@Data
@Slf4j
public class TokenUtilsImpl implements TokenUtils {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserRefreshTokenServiceImpl userRefreshTokenService;

    @Override
    public Claims getClaimsFromJwtToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    public String generateJwtToken(Claims claims) {
        Key signingKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .addClaims(claims)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public JwtResponse createJwtResponse(MyUserDetails user) {
        Map<String, Object> additionalKeyValue = new HashMap<>();
        additionalKeyValue.put(Constants.USER_ID_CLAIMS_NAME, user.getUserID());
        additionalKeyValue.put(Constants.ROLE_CLAIMS_NAME, user.getRole());
//        additionalKeyValue.put(Constants.ACCOUNT_TYPE_CLAIMS_NAME, user.getAccountType());
        long currentMillisecond = System.currentTimeMillis();
        int currentSecond = (int) (currentMillisecond / 1000);
        UUID accessTokenId = UUID.randomUUID();
        UUID refreshTokenId = UUID.randomUUID();
        Date accessTokenExpiration = new Date(currentMillisecond + jwtProperties.getAccessTokenValidity() * 1000);
        Date refreshTokenExpiration = new Date(currentMillisecond + jwtProperties.getRefreshTokenValidity() * 1000);
        //Setup value for access token
        Claims claims = Jwts.claims();
        claims
                .setId(accessTokenId.toString())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(currentMillisecond))
                .setExpiration(accessTokenExpiration)
                .setIssuer("ket_ein17")
                .putAll(additionalKeyValue);
        String accessToken = generateJwtToken(claims);
        //Setup value for refresh token
        claims
                .setId(refreshTokenId.toString())
                .setExpiration(refreshTokenExpiration)
                .put(Constants.ACCESS_TOKEN_ID_CLAIMS_NAME, accessTokenId.toString());
        String refreshToken = generateJwtToken(claims);

        JwtResponse jwtResponse = new JwtResponse(user.getUserID().toString(), user.getUsername(), user.getEmail(), user.getRole().toString(), "Bearer",
                accessToken, refreshToken, accessTokenExpiration);
        //Save refresh token to database
        UserRefreshToken userRefreshToken = new UserRefreshToken(user.getUsername(), refreshTokenId.toString(),
                refreshToken.getBytes(), accessTokenId.toString(),
                currentSecond + jwtProperties.getRefreshTokenValidity());
        userRefreshTokenService.saveRefreshToken(userRefreshToken);
        return jwtResponse;
    }

    @Override
    public Claims checkValidAccessToken(String accessToken) {
        Claims claims;
        try {
            claims = getClaimsFromJwtToken(accessToken);
        } catch (JwtException e) {
            throw new InvalidRequestException(LocalDateTime.now(), Constants.INVALID_TOKEN,
                    e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        if (!userRefreshTokenService.checkValidAccessToken(claims.getId())) {
            throw new InvalidRequestException(LocalDateTime.now(), Constants.INVALID_TOKEN,
                    String.format(Constants.EXPIRED_ACCESS_TOKEN, accessToken), HttpStatus.UNAUTHORIZED);
        }
        return claims;
    }
}
