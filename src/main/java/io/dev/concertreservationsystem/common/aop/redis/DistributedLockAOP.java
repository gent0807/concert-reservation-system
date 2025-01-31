package io.dev.concertreservationsystem.common.aop.redis;

import io.dev.concertreservationsystem.common.util.parser.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
@Profile("dist-lock")
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAOP {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;

   // 분산락 포인트컷
    @Pointcut("@annotation(io.dev.concertreservationsystem.common.aop.redis.DistributedLock)")
    public void distributedLockPointcut() {}

    // Around : 로직 전후로 해줄 것 -> 락 획득과 해제
    @Around("distributedLockPointcut() && @annotation(distributedLock)")
    public Object handleDistributedLock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.lockNm());

        RLock lock = redissonClient.getLock(key);

        try {
            boolean locked = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (locked) {
                return joinPoint.proceed();
            } else {
                log.info("해당 락을 다른 사람이 사용하고 있음");
                throw new IllegalStateException("해당 작업은 현재 다른 프로세스에서 진행 중입니다.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 획득 중 인터럽트 발생", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
