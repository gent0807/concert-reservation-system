package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import io.dev.concertreservationsystem.domain.seat.factory.ReservableSeatFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.SeatInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SeatService {

    private final SeatRepository seatRepository;

    private final ReservationRepository reservationRepository;

    private final ReservableSeatFactory reservableSeatFactory;

    @Validated(SearchReservableSeat.class)
    public List<SeatDTOResult> findReservableSeats(@Valid ConcertDetailDTOParam concertDetailDTOParam) {

        // Seat 타입 객체 생성
        Seat seat = reservableSeatFactory.orderSeat(concertDetailDTOParam.concertDetailId());

        return seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(seat.getConcertDetailId(), seat.getSeatStatus())
                .orElseThrow(()->{
                    log.debug( "Reservable Seat Not Found");
                    throw new SeatInvalidException(ErrorCode.RESERVABLE_SEAT_NOT_FOUND);
                }).stream().map(Seat::convertToSeatDTOResult).collect(Collectors.toList());

    }

    public void updateStatusOfSeats(List<SeatDTOParam> seatDTOParamList, SeatStatusType seatStatus) {
        seatDTOParamList.stream().forEach(
                seatDTOParam -> {
                    Seat seat = seatRepository.findSeatBySeatId(seatDTOParam.seatId());

                    seat.updateSeatStatus(seatStatus);

                    seat.updateExpiredAt(LocalDateTime.now().plusMinutes(5));

                    seatRepository.save(seat);
                }
        );
    }

    public List<SeatDTOParam> convertToSeatDTOParamList(SeatDTOParam seatDTOParam) {
        return reservationRepository.findReservationsByUserIdAndPaymentId(seatDTOParam.userId(), seatDTOParam.paymentId()).orElseThrow(()->{
            throw new PaymentInvalidException(ErrorCode.PAYMENT_NOT_FOUND);
        }).stream().map(Reservation::convertToSeatDTOParam).collect(Collectors.toList());


    }

    public void expireSeatReservation() {
    }
}
