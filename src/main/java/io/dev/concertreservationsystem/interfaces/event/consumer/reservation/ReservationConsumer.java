package io.dev.concertreservationsystem.interfaces.event.consumer.reservation;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {
    private final ReservationService reservationService;

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createReservation(@Payload String kafkaMessage, Acknowledgment acknowledgment){

    }

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updateReservationStatus(@Payload String kafkaMessage, Acknowledgment acknowledgment){

    }
}
