package io.dev.concertreservationsystem.interfaces.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.domain.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestConsumer {

    private final ObjectMapper objectMapper;
    private List<TestEvent> eventRepo = new ArrayList<>();

    @KafkaListener(topics = "testTopic", groupId = "testGroup")
    protected void consume(@Payload String payload, Acknowledgment acknowledgment) throws Exception {
        log.info("recive event : {}", payload);
        TestEvent event = objectMapper.readValue(payload, TestEvent.class);
        eventRepo.add(event);

        // 수동 커밋
        acknowledgment.acknowledge();
    }

    public List<TestEvent> getEventRepo() {
        return eventRepo;
    }
}
