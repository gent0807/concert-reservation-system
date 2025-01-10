package io.dev.concertreservationsystem.domain.seat;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface SeatRepository {
    Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus);

    Seat findSeatBySeatId(Long seatId);

    void save(Seat seat);

    List<Seat> findSeatsByConcertDetailId(Long concertDetailId);

}
