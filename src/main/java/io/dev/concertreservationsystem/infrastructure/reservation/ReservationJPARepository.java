package io.dev.concertreservationsystem.infrastructure.reservation;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ReservationJPARepository extends JpaRepository<Reservation,Long> {

    Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT r from Reservation r WHERE r.userId = :userId AND r.paymentId = :paymentId")
    Optional<List<Reservation>> findReservationsByUserIdAndPaymentIdForShareWithPessimisticLock(@Param("userId") String userId, @Param("paymentId") Long paymentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT r from Reservation r WHERE r.userId = :userId AND r.paymentId = :paymentId")
    Optional<List<Reservation>> findReservationsByUserIdAndPaymentIdForUpdateWithPessimisticLock(@Param("userId") String userId, @Param("paymentId") Long paymentId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT r from Reservation r WHERE r.seatId = :seatId AND r.reservationStatus = :reservationStatus")
    Optional<Reservation> findReservationBySeatIdAndReservationStatusForShareWithPessimisticLock(@Param("seatId") Long seatId, @Param("reservationStatus") ReservationStatusType reservationStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT r from Reservation r WHERE r.seatId = :seatId AND r.reservationStatus = :reservationStatus")
    Optional<Reservation> findReservationBySeatIdAndReservationStatusForUpdateWithPessimisticLock(@Param("seatId") Long seatId, @Param("reservationStatus") ReservationStatusType reservationStatus);

    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "SELECT r from Reservation r WHERE r.userId = :userId AND r.paymentId = :paymentId")
    Optional<List<Reservation>> findReservationsByUserIdAndPaymentIdForUpdateWithOptimisticLock(@Param("userId") String userId, @Param("paymentId") Long paymentId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "SELECT r from Reservation r WHERE r.seatId = :seatId AND r.reservationStatus = :reservationStatus")
    Optional<Reservation> findReservationBySeatIdAndReservationStatusForUpdateWithOptimisticLock(@Param("seatId") Long seatId, @Param("reservationStatus") ReservationStatusType reservationStatus);
}
