package io.dev.concertreservationsystem.domain.common.scheduler;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.application.token.TokenAdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataStatusAdminScheduler {
    private TokenAdminFacade tokenAdminFacade;

    private ConcertReserveAdminFacade concertReserveAdminFacade;

    // 대기열 토큰 active 스케줄러
    @Scheduled(cron = "0 0/2 * * * *")
    public void tokenActiveScheduler() {
         tokenAdminFacade.activeTokens();
    }

    // 대기열 토큰 expired 스케줄러
    @Scheduled(cron = "0 0/1 * * * *")
    public void tokenExpireScheduler() {
        tokenAdminFacade.expiredTokens();

    }

    // 좌석 점유 expired 스케줄러
    @Scheduled(cron = "0 0/4 * * * *")
    public void seatReserveExpireScheduler() {
        concertReserveAdminFacade.expireSeatReservation();
    }

}
