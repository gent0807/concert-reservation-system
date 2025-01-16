package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {
    Optional<List<Seat>> findSeatsByConcertDetailIdAndSeatStatus(Long concertDetailId, SeatStatusType seatStatusReservable);

    List<Seat> findSeatsByConcertDetailId(Long concertDetailId);

    ConcertDetail findConcertDetailIdBySeatId(@Positive @Min(0) Long seatId);

    List<Seat> findSeatsBySeatStatus(SeatStatusType seatStatus);
}
