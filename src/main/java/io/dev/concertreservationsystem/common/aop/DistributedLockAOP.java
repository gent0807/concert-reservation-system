package io.dev.concertreservationsystem.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("redisson")
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAOP {

    // 포인트컷

    // Around
}
