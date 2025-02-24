package io.dev.concertreservationsystem.interfaces.event.listener.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.key.DomainType;
import io.dev.concertreservationsystem.domain.common.producer.Producer;
import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxDTOParam;
import io.dev.concertreservationsystem.domain.outbox.OutboxService;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final Producer producer;

    private final ObjectMapper objectMapper;

    private final OutboxService outboxService;

    // 트랜잭션이 커밋되기 이전에 Outbox 테이블에 예약 성공 정보를 저장해둔다.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(ReservationSuccessEvent event) throws JsonProcessingException {
        outboxService.saveOutbox(OutboxDTOParam.builder()
                                    .aggregateType(DomainType.RESERVATION)
                                    .aggregateId(event.getReservationId())
                                    .eventType(KafkaTopicKey.RESERVATION_SUCCESS_EVENT)
                                    .status(OutboxStatusType.INIT)
                                    .payload(objectMapper.writeValueAsString(event))
                                    .build());
    }

    @Async // 비동기로 실행: 트랜잭션과 상관없이 빠르게 실행하면 되는 경우다
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void produceReservationSuccessEvent(ReservationSuccessEvent event) {
        try{
            producer.produce(KafkaTopicKey.RESERVATION_SUCCESS_EVENT, event.getReservationId().toString(), objectMapper.writeValueAsString(event));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }
}
