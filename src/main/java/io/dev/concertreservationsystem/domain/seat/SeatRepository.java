package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;

import java.util.List;
import java.util.Optional;


public interface SeatRepository {
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus);

    public Optional<Seat> findSeatBySeatIdWithLock(Long seatId);

    public void save(Seat seat);

    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId);

    public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType);
}
