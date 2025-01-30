package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
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

    //@Validated(CreateReservations.class)
    public List<ReservationDTOResult> insertReservations(List<ReservationDTOParam> reservationDTOParamList) {

        return  reservationDTOParamList.stream().map((reservationDTOParam)->{
                    // 도메인 모델 내 정적 팩토리 메소드로 생성
                    Reservation reservation = Reservation.createReservation(reservationDTOParam.userId(), reservationDTOParam.seatId(), reservationDTOParam.paymentId(), ReservationStatusType.TEMP);

                    reservationRepository.save(reservation);

                    return reservationRepository.findReservationByUserIdAndSeatIdAndPaymentId(reservation.getUserId(), reservation.getSeatId(), reservation.getPaymentId())
                            .orElseThrow(()->{
                                throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_SAVE_FAILED, "RESERVATION SERVICE", "insertReservations");
                            }).convertToReservationDTOResult();

        }).collect(Collectors.toList());
    }

    @Validated(ProcessPayment.class)
    public void updateStatusOfReservations(@Valid ReservationDTOParam reservationDTOParam, ReservationStatusType reservationStatusType) {

       reservationRepository.findReservationsByUserIdAndPaymentIdWithLock(reservationDTOParam.userId(), reservationDTOParam.paymentId())
               .orElseThrow(()->{
                   throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, "RESERVATION SERVICE", "updateStatusOfReservations");
               }).stream().forEach((reservation)->{
                   reservation.setReservationStatus(reservationStatusType);
                   reservationRepository.save(reservation);
               });
    }

    @Validated(ProcessPayment.class)
    public List<SeatDTOParam> convertReservationDTOParamToSeatDTOParamList(@Valid ReservationDTOParam reservationDTOParam) {
        return reservationRepository.findReservationsByUserIdAndPaymentIdWithLock(reservationDTOParam.userId(), reservationDTOParam.paymentId()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, "RESERVATION SERVICE", "convertToSeatDTOParamList");
        }).stream().map(Reservation::convertToSeatDTOParam).collect(Collectors.toList());
    }


    public void checkReservationsTemp(ReservationDTOParam reservationDTOParam) {
        List<Reservation> reservationList = reservationRepository.findReservationsByUserIdAndPaymentIdWithLock(reservationDTOParam.userId(), reservationDTOParam.paymentId()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, "RESERVATION SERVICE", "checkReservationsTemp");
        });

        reservationList.stream().forEach(reservation->{
           reservation.checkTemp();
        });
    }
}
