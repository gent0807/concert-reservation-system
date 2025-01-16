package io.dev.concertreservationsystem.interfaces.common.scheduler;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.application.token.TokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataStatusScheduler {
    private TokenFacade tokenFacade;

    private ConcertReserveAdminFacade concertReserveAdminFacade;

    // 대기열 토큰 active 스케줄러
    @Scheduled(cron = "0 0/2 * * * *")
    public void tokenActiveScheduler() {
         tokenFacade.activeTokens();
    }

    // 대기열 토큰 expired 스케줄러
    @Scheduled(cron = "0 0/1 * * * *")
    public void tokenExpireScheduler() {
        tokenFacade.expiredTokens();

    }

    // 좌석 점유 expired 스케줄러
    @Scheduled(cron = "0 0/4 * * * *")
    public void seatReserveExpireScheduler() {
        concertReserveAdminFacade.expireSeatReservation();
    }

}
