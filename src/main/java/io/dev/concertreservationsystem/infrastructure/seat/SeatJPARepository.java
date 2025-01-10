package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJPARepository extends JpaRepository<Seat, Long> {
    Optional<List<Seat>> findSeatsByConcertDetailIdAndSeatStatus(Long concertDetailId, SeatStatusType seatStatusReservable);


    List<Seat> findSeatsByConcertDetailId(Long concertDetailId);
}
