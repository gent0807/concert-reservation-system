package io.dev.concertreservationsystem.interfaces.consumer.point_history;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointHistoryConsumer {
    private final PointHistoryService pointHistoryService;

    @KafkaListener(topics = KafkaTopicKey.POINT_USE_HISTORY_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createPointUseHistory(String kafkaMessage){

    }

    @KafkaListener(topics = KafkaTopicKey.POINT_CHARGE_HISTORY_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createPointChargeHistory(String kafkaMessage){

    }
}
