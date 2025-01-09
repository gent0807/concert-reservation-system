package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.seat.factory.ReservableSeatFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.SeatInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;

    private final ReservableSeatFactory reservableSeatFactory;

    public List<SeatDTOResult> findReservableSeats(ConcertDetailDTOParam concertDetailDTOParam) {

        // Seat 타입 객체 생성
        Seat seat = reservableSeatFactory.orderSeat(concertDetailDTOParam.concertDetailId());

        return seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(seat.getConcertDetailId(), seat.getSeatStatus())
                .orElseThrow(()->{
                    log.debug( "Reservable Seat Not Found");
                    throw new SeatInvalidException(ErrorCode.RESERVABLE_SEAT_NOT_FOUND);
                }).stream().map(Seat::convertToSeatDTOResult).collect(Collectors.toList());

    }
}
