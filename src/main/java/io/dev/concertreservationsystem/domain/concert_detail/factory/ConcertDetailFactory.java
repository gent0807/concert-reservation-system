package io.dev.concertreservationsystem.domain.concert_detail.factory;


import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ConcertDetailInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;

public abstract class ConcertDetailFactory {
    public final ConcertDetail orderConcertDetail(Long concertBasicId) {

        checkConcertDetailValidationOnBuild(concertBasicId);

        ConcertDetail concertDetail = createConcertDetail(concertBasicId);

        return concertDetail;
    }

    public void checkConcertDetailValidationOnBuild(Long concertBasicId){

        checkConcertIdValidation(concertBasicId);


    }

    public void checkConcertIdValidation(Long concertBasicId) {
        if(concertBasicId == null || concertBasicId < 0){
            throw new ConcertDetailInvalidException(ErrorCode.CONCERT_BASIC_ID_INVALID);
        }
    }


    protected abstract ConcertDetail createConcertDetail(Long concertBasicId);
}
