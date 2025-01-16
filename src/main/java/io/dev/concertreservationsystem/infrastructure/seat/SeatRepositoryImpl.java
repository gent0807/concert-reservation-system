package io.dev.concertreservationsystem.infrastructure.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatDTOResult;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJPARepository seatJPARepository;

    @Override
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
        return seatJPARepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
    }

    @Override
    public Optional<Seat> findSeatBySeatId(Long seatId){
        return seatJPARepository.findById(seatId);
    }

    @Override
    public void save(Seat seat){
        seatJPARepository.save(seat);
    }

    @Override
    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
        return seatJPARepository.findSeatsByConcertDetailId(concertDetailId);
    }

    @Override
    public ConcertDetail findConcertDetailBySeatId(Long seatId){
        return seatJPARepository.findConcertDetailIdBySeatId(seatId);
    }

    @Override
    public List<Seat> findSeatsBySeatStatus(SeatStatusType seatStatusType){
        return seatJPARepository.findSeatsBySeatStatus(SeatStatusType.OCCUPIED);
    }


}
