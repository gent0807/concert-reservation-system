package io.dev.concertreservationsystem.domain.reservation;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.reservation.factory.TempReservationFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ReservationInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final TempReservationFactory tempReservationFactory;

    public List<ReservationDTOResult> insertReservations(List<ReservationDTOParam> reservationDTOParamList) {

        return  reservationDTOParamList.stream().map((reservationDTOParam)->{
                    Reservation reservation = tempReservationFactory.orderReservation(reservationDTOParam.userId(), reservationDTOParam.seatId(), reservationDTOParam.paymentId());

                    reservationRepository.saveReservation(reservation);

                    return reservationRepository.findReservationByUserIdAndSeatIdAndPaymentId(reservation.getUserId(), reservation.getSeatId(), reservation.getPaymentId())
                            .orElseThrow(()->{
                                throw new ReservationNotFoundException(ErrorCode.RESERVATION_SAVE_FAILED);
                            }).convertToReservationDTOResult();

        }).collect(Collectors.toList());
    }

    public void updateStatusOfReservations(ReservationDTOParam reservationDTOParam) {

       reservationRepository.findReservationsByUserIdAndPaymentId(reservationDTOParam.userId(), reservationDTOParam.paymentId())
               .orElseThrow(()->{
                   throw new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND);
               }).stream().map((reservation)->{
                   reservation.setReservationStatus(ReservationStatusType.CONFIRMED);
                   reservationRepository.saveReservation(reservation);
                   return reservation;
               });
    }

    public List<ReservationDTOParam> convertToReservationDTOParamList(ReservationDTOParam reservationDTOParam) {
        return reservationRepository.findReservationsByUserIdAndPaymentId(reservationDTOParam.userId(), reservationDTOParam.paymentId())
                .orElseThrow(()->{
                    throw new PaymentNotFoundException(ErrorCode.PAYMENT_NOT_FOUND);
                }).stream().map(Reservation::convertToReservationDTOParam).collect(Collectors.toList());
    }
}
