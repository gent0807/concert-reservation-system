package io.dev.concertreservationsystem.domain.seat.factory;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ConcertDetailInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.SeatInvalidException;

public abstract class SeatFactory {
    public final Seat orderSeat(Long concertDetailId) {

        checkSeatValidationOnBuild(concertDetailId);

        Seat seat = createConcertDetail(concertDetailId);

        return seat;
    }

    public void checkSeatValidationOnBuild(Long concertDetailId){

        checkConcertDetailIdValidation(concertDetailId);


    }

    public void checkConcertDetailIdValidation(Long concertDetailId) {
        if(concertDetailId == null || concertDetailId < 0){
            throw new SeatInvalidException(ErrorCode.CONCERT_DETAIL_ID_INVALID);
        }
    }


    protected abstract Seat createConcertDetail(Long concertDetailId);
}
