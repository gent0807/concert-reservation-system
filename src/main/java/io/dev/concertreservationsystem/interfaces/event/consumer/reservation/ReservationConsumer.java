package io.dev.concertreservationsystem.interfaces.event.consumer.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.data_platform.DataPlatformService;
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

    private final DataPlatformService dataPlatformService;

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createReservation(@Payload String message, Acknowledgment acknowledgment){

    }

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updateReservationStatus(@Payload String message, Acknowledgment acknowledgment){

    }

    @KafkaListener(topics = KafkaTopicKey.RESERVATION_SUCCESS_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void sendReservationSuccessToDataPlatform(@Payload String message, Acknowledgment acknowledgment) throws JsonProcessingException {


        dataPlatformService.sendReservationSuccessData(message);


        acknowledgment.acknowledge();

    }
}
