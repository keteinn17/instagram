package security.instagram.config.user_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.instagram.config.token.JwtResponse;
import security.instagram.config.token.TokenUtils;
import security.instagram.dto.LoginRequest;
import security.instagram.dto.MyUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Slf4j
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenUtils tokenUtils;
    public MyUsernamePasswordAuthenticationFilter(TokenUtils tokenUtils) {
        super(new AntPathRequestMatcher("/v1/auth/login", "POST"));
        this.tokenUtils = tokenUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        if (!httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }

        LoginRequest loginRequest = getLoginRequest(httpServletRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        setDetails(httpServletRequest, usernamePasswordAuthenticationToken);
        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) {
        LoginRequest loginRequest = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            ObjectMapper objectMapper = new ObjectMapper();
            loginRequest = objectMapper.readValue(reader, LoginRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        if (loginRequest == null) {
            loginRequest = new LoginRequest();
        }
        return loginRequest;
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        response.addHeader("Authorization", "Bearer " );
        MyUserDetails userDetails= (MyUserDetails) authResult.getPrincipal();
        JwtResponse jwtResponse = tokenUtils.createJwtResponse(userDetails);
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtResponse));
        response.setContentType("application/json");
        response.getWriter().flush();
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + failed.getMessage() + "\"}");
        response.getWriter().flush();
    }
}

