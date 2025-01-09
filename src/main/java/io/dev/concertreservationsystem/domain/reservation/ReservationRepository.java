package io.dev.concertreservationsystem.domain.reservation;

import java.util.Optional;

public interface ReservationRepository {
    void saveReservation(Reservation reservation);

    Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId);
}
