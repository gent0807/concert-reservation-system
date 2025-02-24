package io.dev.concertreservationsystem.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.domain.TestEvent;
import io.dev.concertreservationsystem.infrastructure.common.producer.KafkaProducer;
import io.dev.concertreservationsystem.interfaces.consumer.TestConsumer;
import io.dev.concertreservationsystem.interfaces.consumer.seat.SeatConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:29092"
        },
        ports = { 29092 })
public class EmbeddedKafkaTest {
    @Autowired
    KafkaProducer producer;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestConsumer consumer;


    @Test
    void test() throws Exception {
        // given
        TestEvent event = TestEvent.idOf(1L);
        String payload = objectMapper.writeValueAsString(event);

        // when
        producer.produce("testTopic", Long.toString(event.getId()), payload);
        Thread.sleep(2000);

        // then
        assertNotEquals(0, consumer.getEventRepo().size());
        assertEquals(1, consumer.getEventRepo().size());
        assertEquals(event, consumer.getEventRepo().get(0));
    }


}
