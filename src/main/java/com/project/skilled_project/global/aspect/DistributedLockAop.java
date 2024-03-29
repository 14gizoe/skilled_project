package com.project.skilled_project.global.aspect;

import com.project.skilled_project.global.annotation.WithDistributedLock;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DistributedLockAop {

  private final RedissonClient redissonClient;

  @Autowired
  public DistributedLockAop(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @Around("@annotation(withDistributedLock)")
  public Object aroundAdvice(ProceedingJoinPoint joinPoint, WithDistributedLock withDistributedLock) throws Throwable {
    String lockKey = withDistributedLock.lockName();
    long waitTime = withDistributedLock.waitTime();
    long leaseTime = withDistributedLock.leaseTime();
    RLock lock = redissonClient.getFairLock(lockKey);

    try {
      boolean isLocked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
      if (isLocked) {
        try {
          return joinPoint.proceed();
        } finally {
          lock.unlock();
        }
      } else {
        throw new IllegalStateException("다른 유저가 접근중에 있습니다. 다시 시도해 주세요");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw e;
    }
  }
}
