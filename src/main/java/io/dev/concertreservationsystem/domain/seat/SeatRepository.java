package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface SeatRepository {
    Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus);

    Optional<Seat> findSeatBySeatId(Long seatId);

    void save(Seat seat);

    List<Seat> findSeatsByConcertDetailId(Long concertDetailId);

    ConcertDetail findConcertDetailBySeatId(Long seatId);
}
