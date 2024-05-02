package com.security.secretstore.core.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String key, String value, long lifetime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, lifetime, TimeUnit.MILLISECONDS);
    }

    public String getToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}

