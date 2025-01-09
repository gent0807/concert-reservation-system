package io.dev.concertreservationsystem.domain.concert_detail.factory;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import org.springframework.stereotype.Component;

@Component
public class ReservableConcertDetailFactory extends ConcertDetailFactory {
    @Override
    public ConcertDetail createConcertDetail(Long concertBasicId){
        return ConcertDetail.builder()
                .concertBasicId(concertBasicId)
                .concertDetailStatus(ConcertDetailStatusType.RESERVABLE)
                .build();

    }
}
