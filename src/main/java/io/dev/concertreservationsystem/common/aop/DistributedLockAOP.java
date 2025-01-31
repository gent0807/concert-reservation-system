package io.dev.concertreservationsystem.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
@Profile("redisson")
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAOP {

    // 포인트컷

    // Around : 로직 전후로 해줄 것 -> 락 획득과 해제


}
