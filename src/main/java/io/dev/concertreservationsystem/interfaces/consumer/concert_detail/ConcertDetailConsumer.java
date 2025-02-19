package io.dev.concertreservationsystem.interfaces.consumer.concert_detail;

import io.dev.concertreservationsystem.common.config.kafka.KafkaKey;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertDetailConsumer {

    private final ConcertDetailService concertDetailService;

    @KafkaListener(topics = KafkaKey.CONCERT_DETAIL_STATUS_UPDATE_EVENT)
    public void updateConcertDetailStatus(String kafkaMessage){

    }
}
