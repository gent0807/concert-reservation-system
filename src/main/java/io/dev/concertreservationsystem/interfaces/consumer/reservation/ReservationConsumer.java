package io.dev.concertreservationsystem.interfaces.consumer.reservation;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {
    private final ReservationService reservationService;

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createReservation(String kafkaMessage){

    }

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updateReservationStatus(String kafkaMessage){

    }
}
