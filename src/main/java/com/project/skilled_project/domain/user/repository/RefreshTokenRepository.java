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

  public void delete(String username) {
    redisTemplate.delete(username);
  }

  public void save(String username, String refreshToken) {
    valueOperations.set(username, refreshToken);
    redisTemplate.expire(username, 7L, TimeUnit.DAYS);
  }

  public boolean existsByKey(String key) {
    return valueOperations.getOperations().hasKey(key);
  }
}
