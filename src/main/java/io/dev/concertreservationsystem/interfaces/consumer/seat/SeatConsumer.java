package io.dev.concertreservationsystem.interfaces.consumer.seat;

import io.dev.concertreservationsystem.common.config.kafka.KafkaKey;
import io.dev.concertreservationsystem.domain.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatConsumer {

    private final SeatService seatService;

    @KafkaListener(topics = KafkaKey.SEAT_STATUS_UPDATE_EVENT)
    public void updateSeatStatus(String kafkaMessage){

    }
}
