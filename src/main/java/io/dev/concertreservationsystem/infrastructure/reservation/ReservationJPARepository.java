package io.dev.concertreservationsystem.infrastructure.reservation;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.seat.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservationJPARepository extends JpaRepository<Reservation,Long> {

    Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId);

    Optional<List<Reservation>> findReservationsByUserIdAndPaymentId(String userId, Long paymentId);

    Optional<List<Seat>> findSeatsByUserIdAndPaymentId(String userId, Long paymentId);
}
