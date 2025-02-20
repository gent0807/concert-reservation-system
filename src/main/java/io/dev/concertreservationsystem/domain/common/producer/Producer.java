package io.dev.concertreservationsystem.domain.common.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String topic, String key, String payload){
        kafkaTemplate.send(topic, key, payload);
    }
}
