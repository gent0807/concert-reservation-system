package io.dev.concertreservationsystem.interfaces.event.consumer.concert_detail;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertDetailConsumer {

    private final ConcertDetailService concertDetailService;

    @KafkaListener(topics = KafkaTopicKey.CONCERT_DETAIL_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updateConcertDetailStatus(@Payload String kafkaMessage, Acknowledgment acknowledgment){

    }
}
