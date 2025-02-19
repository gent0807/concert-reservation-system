package io.dev.concertreservationsystem.interfaces.consumer.reservation;

import io.dev.concertreservationsystem.common.config.kafka.KafkaKey;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationConsumer {
    private final ReservationService reservationService;

    @KafkaListener(topics = KafkaKey.RESERVATION_CREATE_EVENT, groupId = KafkaKey.CONSUMER_GROUP_ID)
    public void createReservation(String kafkaMessage){

    }

    @KafkaListener(topics = KafkaKey.RESERVATION_STATUS_UPDATE_EVENT, groupId = KafkaKey.CONSUMER_GROUP_ID)
    public void updateReservationStatus(String kafkaMessage){

    }
}
