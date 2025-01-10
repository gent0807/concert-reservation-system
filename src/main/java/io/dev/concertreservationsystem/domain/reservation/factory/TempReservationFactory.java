package io.dev.concertreservationsystem.domain.reservation.factory;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import org.springframework.stereotype.Component;

@Component
public class TempReservationFactory extends ReservationFactory {
    @Override
    public Reservation createReservation(String userId, Long seatId, Long paymentId){
        return Reservation.builder()
                .userId(userId)
                .seatId(seatId)
                .paymentId(paymentId)
                .reservationStatus(ReservationStatusType.TEMP)
                .build();
    }
}
