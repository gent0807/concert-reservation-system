package io.dev.concertreservationsystem.interfaces.scheduler;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.application.token.TokenFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataStatusScheduler {


    private final TokenFacade tokenFacade;

    private final ConcertReserveAdminFacade concertReserveAdminFacade;


    @Value("${token.max.active.limit}")
    private long maxTokenActiveLimit;

    // 대기열 토큰 active 스케줄러
    @Scheduled(cron = "0 0/2 * * * *")
    public void tokenActiveScheduler() {

        String uuid = "SCHEDULER-" + UUID.randomUUID();

        long startTime = System.currentTimeMillis();

        log.info("UUID - [{}] | <<--------------------------------------------------------------------------", uuid);
        log.info("UUID - [{}] | ACTIVE_TOKEN_SCHEDULER | START TIME : {}", uuid, LocalDateTime.now());

        tokenFacade.activeTokens(maxTokenActiveLimit);

        log.info("UUID - [{}] | schedule processed in {} ms : {}", uuid, (System.currentTimeMillis() - startTime), LocalDateTime.now());
        log.info("UUID - [{}] | END TIME : {}", uuid, LocalDateTime.now());
        log.info("UUID - [{}] | -------------------------------------------------------------------------->>", uuid);
    }

    // 대기열 토큰 expired 스케줄러
    @Scheduled(cron = "0 0/1 * * * *")
    public void tokenExpireScheduler() {

        String uuid = "SCHEDULER-" + UUID.randomUUID();

        long startTime = System.currentTimeMillis();

        log.info("UUID - [{}] | <<--------------------------------------------------------------------------", uuid);
        log.info("UUID - [{}] | EXPIRE_TOKEN_SCHEDULER | START TIME : {}", uuid, LocalDateTime.now());

        tokenFacade.expiredTokens();

        log.info("UUID - [{}] | schedule processed in {} ms : {}", uuid, (System.currentTimeMillis() - startTime), LocalDateTime.now());
        log.info("UUID - [{}] | END TIME : {}", uuid, LocalDateTime.now());
        log.info("UUID - [{}] | -------------------------------------------------------------------------->>", uuid);
    }

    // 좌석 점유 expired 스케줄러
    @Scheduled(cron = "0 0/4 * * * *")
    public void seatReserveExpireScheduler() {

        String uuid = "SCHEDULER-" + UUID.randomUUID();

        long startTime = System.currentTimeMillis();

        log.info("UUID - [{}] | <<--------------------------------------------------------------------------", uuid);
        log.info("UUID - [{}] | SEAT_SCHEDULER | START TIME : {}", uuid, LocalDateTime.now());

        concertReserveAdminFacade.expireSeatReservation();

        log.info("UUID - [{}] | schedule processed in {} ms : {}", uuid, (System.currentTimeMillis() - startTime), LocalDateTime.now());
        log.info("UUID - [{}] | END TIME : {}", uuid, LocalDateTime.now());
        log.info("UUID - [{}] | -------------------------------------------------------------------------->>", uuid);
    }

}
