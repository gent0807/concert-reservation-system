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
@Profile(value = "pessimistic-lock")
public class PessimisticSeatRepositoryImpl implements SeatRepository {
    private final SeatJPARepository pessimisticSeatRepository;

    @Override
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
        return pessimisticSeatRepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
    }

    @Override
    public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
        return pessimisticSeatRepository.findSeatBySeatIdForUpdateWithPessimisticLock(seatId);
    }

    @Override
    public void save(Seat seat){
        pessimisticSeatRepository.save(seat);
    }

    @Override
    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
        return pessimisticSeatRepository.findSeatsByConcertDetailId(concertDetailId);
    }

    @Override
    public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
        return pessimisticSeatRepository.findSeatsBySeatStatusForUpdateWithPessimisticLock(SeatStatusType.OCCUPIED);
    }
}
