package io.dev.concertreservationsystem.infrastructure.common.producer;

import io.dev.concertreservationsystem.domain.common.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void produce(String topic, String key, String payload){
        kafkaTemplate.send(topic, key, payload);
    }
}
