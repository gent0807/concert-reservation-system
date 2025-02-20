package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.common.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:29092"
        },
        ports = { 29092 })
public class ConcertReservationMessageQueueIntegrationTest {

        @Autowired
        Producer producer;

}
