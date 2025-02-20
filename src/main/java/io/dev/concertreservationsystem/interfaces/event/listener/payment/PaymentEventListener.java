package io.dev.concertreservationsystem.interfaces.event.listener.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.producer.Producer;
import io.dev.concertreservationsystem.domain.outbox.OutboxDTOParam;
import io.dev.concertreservationsystem.domain.outbox.OutboxService;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentSuccessEvent;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final Producer producer;

    private final ObjectMapper objectMapper;

    private final OutboxService outboxService;

    // 트랜잭션이 커밋되기 이전에 Outbox 테이블에 결제 성공 정보를 저장해둔다.
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(PaymentSuccessEvent event) throws JsonProcessingException {
        outboxService.saveOutbox(OutboxDTOParam.builder()
                                    .aggregateType(KafkaTopicKey.PAYMENT_SUCCESS_EVENT)
                                    .aggregateId(event.getPaymentId())
                                    .eventType(KafkaTopicKey.PAYMENT_SUCCESS_EVENT)
                                    .status(OutboxStatusType.INIT)
                                    .payload(objectMapper.writeValueAsString(event))
                                    .build());
    }

    @Async // 비동기로 실행: 트랜잭션과 상관없이 빠르게 실행하면 되는 경우다
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void producePaymentSuccessEvent(PaymentSuccessEvent event) {
        try{
            producer.produce(KafkaTopicKey.PAYMENT_SUCCESS_EVENT, event.getPaymentId().toString(), objectMapper.writeValueAsString(event));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }
}
