package io.dev.concertreservationsystem.domain.reservation.factory;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ReservationInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.SeatInvalidException;

public abstract class ReservationFactory {
    public final Reservation orderReservation(String userId, Long seatId, Long paymentId) {

        checkReservationValidationOnBuild(userId, seatId, paymentId);

        Reservation reservation = createReservation(userId, seatId, paymentId);

        return reservation;
    }

    public void checkReservationValidationOnBuild(String userId, Long seatId, Long paymentId){

        checkUserIdValidation(userId);

        checkSeatIdValidation(seatId);

        checkPaymentIdValidation(paymentId);

    }

    private void checkPaymentIdValidation(Long paymentId) {
        if(paymentId == null || paymentId < 0){
            throw new ReservationInvalidException(ErrorCode.RESERVATION_PAYMENT_ID_INVALID);
        }
    }

    private void checkSeatIdValidation(Long seatId) {
        if(seatId == null || seatId < 0){
            throw new ReservationInvalidException(ErrorCode.RESERVATION_SEAT_ID_INVALID);
        }
    }

    public void checkUserIdValidation(String userId) {
        if(userId == null || userId.isBlank()){
            throw new ReservationInvalidException(ErrorCode.RESERVATION_USER_ID_INVALID);
        }
    }


    protected abstract Reservation createReservation(String userId, Long seatId, Long paymentId);

}
