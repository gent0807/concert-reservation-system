package io.dev.concertreservationsystem.interfaces.event.listener.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.producer.Producer;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final Producer producer;

    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(ReservationSuccessEvent event) {

    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void produceReservationSuccessEvent(ReservationSuccessEvent event) {
        try{
            producer.produce(KafkaTopicKey.RESERVATION_SUCCESS_EVENT, event.getReservationId().toString(), objectMapper.writeValueAsString(event));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }
}
