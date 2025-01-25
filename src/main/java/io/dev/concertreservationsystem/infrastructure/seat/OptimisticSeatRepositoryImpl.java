package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "optimistic-lock")
public class OptimisticSeatRepositoryImpl implements SeatRepository {
    private final OptimisticSeatRepository optimisticSeatRepository;

    @Override
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
        return optimisticSeatRepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
    }

    @Override
    public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
        return optimisticSeatRepository.findSeatBySeatIdForUpdate(seatId);
    }

    @Override
    public void save(Seat seat){
        optimisticSeatRepository.save(seat);
    }

    @Override
    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
        return optimisticSeatRepository.findSeatsByConcertDetailId(concertDetailId);
    }

    @Override
    public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
        return optimisticSeatRepository.findSeatsBySeatStatusForUpdate(SeatStatusType.OCCUPIED);
    }
}
