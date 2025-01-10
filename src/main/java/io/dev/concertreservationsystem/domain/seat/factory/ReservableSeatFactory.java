package io.dev.concertreservationsystem.domain.seat.factory;

import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import org.springframework.stereotype.Component;

@Component
public class ReservableSeatFactory extends SeatFactory {
    @Override
    public Seat createConcertDetail(Long concertDetailId){
        return Seat.builder()
                .concertDetailId(concertDetailId)
                .seatStatus(SeatStatusType.RESERVABLE)
                .build();
    }


}
