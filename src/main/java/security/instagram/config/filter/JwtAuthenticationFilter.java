package security.instagram.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.instagram.config.auth.BearerContext;
import security.instagram.config.auth.BearerContextHolder;
import security.instagram.config.token.ErrorResponse;
import security.instagram.config.token.TokenUtils;
import security.instagram.utils.Constants;
import security.instagram.utils.exception.InvalidRequestException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtils tokenUtils;

    @Autowired
    public JwtAuthenticationFilter(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getJwtToken(httpServletRequest);
            if (jwtToken != null) {
                saveToSecurityContext(jwtToken);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        catch (InvalidRequestException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getTimestamp(), e.getError(), e.getError_description(), e.getHttpStatus());
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        } finally{
            BearerContextHolder.clearContext();
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        if (request.getHeader(Constants.AUTHORIZATION_HEADER) != null
                && request.getHeader(Constants.AUTHORIZATION_HEADER).startsWith(Constants.TOKEN_PREFIX)) {
            return request.getHeader(Constants.AUTHORIZATION_HEADER).replace(Constants.TOKEN_PREFIX, "");
        }
        return null;
    }

    private void saveToSecurityContext(String jwtToken) {
        Claims claims = tokenUtils.checkValidAccessToken(jwtToken);

        Optional<Integer> userId = Optional.ofNullable((Integer) claims.get(Constants.USER_ID_CLAIMS_NAME));
        Optional<String> email = Optional.ofNullable(claims.getSubject());
        Optional<String> role = Optional.ofNullable((String) claims.get(Constants.ROLE_CLAIMS_NAME));
//        Optional<String> accountType = (Optional.ofNullable( (String)claims.get(Constants.ACCOUNT_TYPE_CLAIMS_NAME)));
        BearerContext bearerContext = BearerContextHolder.getContext();
        bearerContext.setBearerToken(jwtToken);
        bearerContext.setUserId(String.valueOf(userId.orElseThrow(() ->
                new InvalidRequestException(LocalDateTime.now(), Constants.INVALID_TOKEN,
                        Constants.TOKEN_INVALID_USER_ID, HttpStatus.UNAUTHORIZED)))
        );
        bearerContext.setEmail(email.orElseThrow(() ->
                new InvalidRequestException(LocalDateTime.now(), Constants.INVALID_TOKEN,
                        Constants.TOKEN_INVALID_USER_NAME, HttpStatus.UNAUTHORIZED))
        );
        bearerContext.setRole(role.get());

//        bearerContext.setAccountType(accountType.orElseThrow(() ->
//                new InvalidRequestException(LocalDateTime.now(), Constants.INVALID_TOKEN,
//                        Constants.TOKEN_INVALID_ROLE, HttpStatus.UNAUTHORIZED))
//        );
        Collection<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role.get());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email.get(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
