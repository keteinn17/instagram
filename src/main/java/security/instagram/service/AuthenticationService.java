//package security.instagram.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import security.instagram.config.token.JwtResponse;
//import security.instagram.config.token.TokenUtils;
//import security.instagram.dto.LoginRequest;
//import security.instagram.dto.MyUserDetails;
//import security.instagram.entity.User;
//import security.instagram.repository.UserRepository;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//
///**
// * @author ket_ein17
// * @Date 5/27/2024
// */
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//public class AuthenticationService {
//    private final UserRepository repository;
//    @Autowired
//    private final AuthenticationManager authenticationManager;
//    @Autowired private TokenUtils tokenUtils;
//
//    public void authenticate(LoginRequest request, HttpServletRequest httpServletRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//    }
//
//}
