package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface SeatRepository {
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus);

    public Optional<Seat> findSeatBySeatId(Long seatId);

    public void save(Seat seat);

    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId);

    public ConcertDetail findConcertDetailBySeatId(Long seatId);

    public List<Seat> findSeatsBySeatStatus(SeatStatusType seatStatusType);
}
