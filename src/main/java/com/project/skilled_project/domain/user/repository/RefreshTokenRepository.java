package com.project.skilled_project.domain.user.repository;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

  private final RedisTemplate redisTemplate;

  @Resource(name = "redisTemplate")
  private ValueOperations<String, String> valueOperations;


  public void delete(Long userId) {
    redisTemplate.delete(String.valueOf(userId));
  }

  public void save(Long userId, String refreshToken) {
    valueOperations.set(String.valueOf(userId), refreshToken);
    redisTemplate.expire(String.valueOf(userId), 7L, TimeUnit.DAYS);
  }

  public String findById(Long userId) {
    String refreshToken = valueOperations.get(String.valueOf(userId));

    if (userId == null) {
      return null;
    }

    return refreshToken;
  }
}
