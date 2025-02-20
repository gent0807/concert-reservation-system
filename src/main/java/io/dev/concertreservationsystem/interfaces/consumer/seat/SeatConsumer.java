package io.dev.concertreservationsystem.interfaces.consumer.seat;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatConsumer {

    private final SeatService seatService;

    @KafkaListener(topics = KafkaTopicKey.SEAT_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updateSeatStatus(@Payload String kafkaMessage, Acknowledgment acknowledgment){

    }
}
