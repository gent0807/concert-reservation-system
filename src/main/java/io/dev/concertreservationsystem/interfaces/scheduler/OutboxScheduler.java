package io.dev.concertreservationsystem.interfaces.scheduler;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.key.DomainType;
import io.dev.concertreservationsystem.domain.common.producer.Producer;
import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxService;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final Producer producer;

    private final OutboxService outboxService;

    // 특정 주기로, 데이터 플랫폼에 전송되지 못한 메시지들 전송하기 위한 이벤트 재발행 스케줄러
    @Scheduled(fixedDelay = 70000)
    public void republishEvent() {
        List<Outbox> outboxList = outboxService.findOutboxToRepublish(OutboxStatusType.INIT, 70);

        outboxList.stream().forEach(outbox -> {
            if(outbox.getAggregateType() == DomainType.RESERVATION){
                producer.produce(KafkaTopicKey.RESERVATION_SUCCESS_EVENT, outbox.getAggregateId().toString(), outbox.getPayload());
            }else if(outbox.getAggregateType() == DomainType.PAYMENT){
                producer.produce(KafkaTopicKey.PAYMENT_SUCCESS_EVENT, outbox.getAggregateId().toString(), outbox.getPayload());
            }
        });
    }

    // 매일 특정 시각마다, 오랜 기간 데이터 플랫폼에 전송되지 못한 메시지들 outbox 테이블에서 삭제하는 Outbox 삭제 스케줄러
    @Scheduled(cron = "0 59 23 * * ?")
    public void removeExpiredOutbox() {
        outboxService.removeExpiredOutbox(OutboxStatusType.INIT, 30);
    }
}
