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
@Profile("default")
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJPARepository seatJPARepository;

    @Override
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
        return seatJPARepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
    }

    @Override
    public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
        return seatJPARepository.findSeatBySeatIdForShareWithPessimisticLock(seatId);
    }

    @Override
    public void save(Seat seat){
        seatJPARepository.saveAndFlush(seat);
    }

    @Override
    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
        return seatJPARepository.findSeatsByConcertDetailId(concertDetailId);
    }

    @Override
    public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
        return seatJPARepository.findSeatsBySeatStatusForShareWithPessimisticLock(SeatStatusType.OCCUPIED);
    }
}
