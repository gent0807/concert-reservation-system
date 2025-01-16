package io.dev.concertreservationsystem.domain.reservation;


import io.dev.concertreservationsystem.domain.seat.Seat;


import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    void saveReservation(Reservation reservation);

    Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId);

    Optional<List<Reservation>> findReservationsByUserIdAndPaymentId(String userId, Long paymentId);

    Reservation findReservationBySeatId(Long seatId);
}