package org.example.service;

import org.example.entities.RefreshTokens;
import org.example.entities.UserInfo;
import org.example.respository.RefreshTokenRepository;
import org.example.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired UserRepository userRepository;

    public RefreshTokens createRefreshToken(String username){
        UserInfo userInfoExtracted = userRepository.findByUserName(username);
        RefreshTokens refreshTokens = RefreshTokens.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();

        //we can also use this....
        //RefreshTokens refreshTokens = new RefreshTokens(userInfoExtracted, UUID.randomUUID().toString(), Instant.now().plusMillis(600000));

        return refreshTokenRepository.save(refreshTokens);
    }

    public RefreshTokens verifyExpiration(RefreshTokens refreshTokens){
        if(refreshTokens.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshTokens);
            throw new RuntimeException(refreshTokens.getToken() + " Refresh Token Expired, Please make a new Login");
        }
        return refreshTokens;
    }

    public Optional<RefreshTokens> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
