package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {
    Optional<List<Seat>> findSeatsByConcertDetailIdAndSeatStatus(Long concertDetailId, SeatStatusType seatStatusReservable);

    List<Seat> findSeatsByConcertDetailId(Long concertDetailId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT s from Seat s WHERE s.seatStatus = :seatStatus")
    Optional<List<Seat>> findSeatsBySeatStatusForUpdate(@Param("seatStatus") SeatStatusType seatStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT s from Seat s WHERE s.seatId = :seatId")
    Optional<Seat> findSeatBySeatIdForUpdate(@Param("seatId") Long seatId);
}
