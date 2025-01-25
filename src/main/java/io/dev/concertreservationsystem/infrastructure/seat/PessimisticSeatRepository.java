package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PessimisticSeatRepository extends SeatJPARepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT s from Seat s WHERE s.seatStatus = :seatStatus")
    Optional<List<Seat>> findSeatsBySeatStatusForUpdate(@Param("seatStatus") SeatStatusType seatStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT s from Seat s WHERE s.seatId = :seatId")
    Optional<Seat> findSeatBySeatIdForUpdate(@Param("seatId") Long seatId);
}
