package io.dev.concertreservationsystem.domain.concert_detail.factory;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatus;
import org.springframework.stereotype.Component;

@Component
public class ReservableConcertDetailFactory extends ConcertDetailFactory {
    @Override
    public ConcertDetail createConcertDetail(Long concertBasicId){
        return ConcertDetail.builder()
                .concertBasicId(concertBasicId)
                .concertDetailStatus(ConcertDetailStatus.RESERVABLE)
                .build();

    }
}
