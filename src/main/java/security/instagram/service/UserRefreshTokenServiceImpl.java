package security.instagram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import security.instagram.entity.UserRefreshToken;
import security.instagram.repository.UserRefreshTokenRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Service
@Slf4j
@Transactional
public class UserRefreshTokenServiceImpl implements UserRefreshTokenService {
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    public UserRefreshTokenServiceImpl(UserRefreshTokenRepository userRefreshTokenRepository) {
        this.userRefreshTokenRepository = userRefreshTokenRepository;
    }

    @Override
    public UserRefreshToken saveRefreshToken(UserRefreshToken userRefreshToken) {
        Optional<UserRefreshToken> existingUserRefreshToken =
                userRefreshTokenRepository.findById(userRefreshToken.getEmail());
        if (existingUserRefreshToken.isPresent()) {
            BeanUtils.copyProperties(userRefreshToken, existingUserRefreshToken.get(), "email");
            return userRefreshTokenRepository.save(existingUserRefreshToken.get());
        }
        return userRefreshTokenRepository.save(userRefreshToken);
    }

    public boolean checkValidAccessToken(String tokenId) {
        //UUID uuid = UUID.fromString(tokenId);
        return userRefreshTokenRepository.existsByAti(tokenId);
    }

    @Override
    public UserRefreshToken getRefreshToken(UUID ati) {
        return userRefreshTokenRepository.findByAti(ati);
    }

    @Override
    public UserRefreshToken getRefreshToken(String email) {
        return userRefreshTokenRepository.findByEmail(email);
    }
}
