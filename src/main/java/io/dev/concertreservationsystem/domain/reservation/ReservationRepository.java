package io.dev.concertreservationsystem.domain.reservation;


import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId);

    Optional<List<Reservation>> findReservationsByUserIdAndPaymentIdWithLock(String userId, Long paymentId);

    Optional<Reservation> findReservationBySeatIdAndStatusWithLock(Long seatId, ReservationStatusType reservationStatusType);
}