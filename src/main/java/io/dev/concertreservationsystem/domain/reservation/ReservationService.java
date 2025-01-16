package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.exception.error.PaymentInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.PaymentNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ReservationNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final SeatRepository seatRepository;

    @Validated(CreateReservations.class)
    public List<ReservationDTOResult> insertReservations(List<@Valid ReservationDTOParam> reservationDTOParamList) {

        return  reservationDTOParamList.stream().map((reservationDTOParam)->{
                    // 도메인 모델 내 정적 팩토리 메소드로 생성
                    Reservation reservation = Reservation.createReservation(reservationDTOParam.userId(), reservationDTOParam.seatId(), reservationDTOParam.paymentId(), ReservationStatusType.TEMP);

                    reservationRepository.saveReservation(reservation);

                    return reservationRepository.findReservationByUserIdAndSeatIdAndPaymentId(reservation.getUserId(), reservation.getSeatId(), reservation.getPaymentId())
                            .orElseThrow(()->{
                                log.error("reservation save failed");
                                throw new ReservationNotFoundException(ErrorCode.RESERVATION_SAVE_FAILED);
                            }).convertToReservationDTOResult();

        }).collect(Collectors.toList());
    }

    @Validated(ProcessPayment.class)
    public void updateStatusOfReservations(@Valid ReservationDTOParam reservationDTOParam) {

       reservationRepository.findReservationsByUserIdAndPaymentId(reservationDTOParam.userId(), reservationDTOParam.paymentId())
               .orElseThrow(()->{
                   log.error("reservation not found");
                   throw new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
               }).stream().map((reservation)->{
                   reservation.setReservationStatus(ReservationStatusType.CONFIRMED);
                   reservationRepository.saveReservation(reservation);
                   return reservation;
               });
    }

    @Validated(ProcessPayment.class)
    public List<SeatDTOParam> convertToSeatDTOParamList(@Valid ReservationDTOParam reservationDTOParam) {
        return reservationRepository.findReservationsByUserIdAndPaymentId(reservationDTOParam.userId(), reservationDTOParam.paymentId()).orElseThrow(()->{
            log.error("reservation not found");
            throw new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
        }).stream().map(Reservation::convertToSeatDTOParam).collect(Collectors.toList());
    }


    @Validated(ProcessPayment.class)
    public List<ConcertDetailDTOParam> convertToConcertDetailDTOParamList(@Valid ReservationDTOParam reservationDTOParam) {
        return reservationRepository.findReservationsByUserIdAndPaymentId(reservationDTOParam.userId(), reservationDTOParam.paymentId())
                .orElseThrow(()->{
                    log.error("reservation not found");
                    throw new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
                }).stream().map(reservation -> {
                    return seatRepository.findConcertDetailBySeatId(reservation.getSeatId()).convertToConcertDetailDTOParam();
                }).collect(Collectors.toList());
    }
}
