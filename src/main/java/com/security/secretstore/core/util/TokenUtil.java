package com.security.secretstore.core.util;

import com.security.secretstore.core.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    private final Long expirationTime;
    private final TokenRepository tokenRepository;

    public TokenUtil(
            @Value("${app.token.expiration_time}") Long expirationTime,
            TokenRepository tokenRepository
    ){
        this.expirationTime = expirationTime;
        this.tokenRepository = tokenRepository;
    }

    public String createToken(String data) {
        String token = UUID.randomUUID().toString();
        tokenRepository.save(token, data, expirationTime);
        return token;
    }

    public String getToken(String key) {
        return tokenRepository.getToken(key);
    }
}
