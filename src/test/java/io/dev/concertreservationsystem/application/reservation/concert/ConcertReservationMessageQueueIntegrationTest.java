package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.common.key.DomainType;
import io.dev.concertreservationsystem.domain.common.producer.Producer;
import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxRepository;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentSuccessEvent;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:29092"
        },
        ports = { 29092 })
public class ConcertReservationMessageQueueIntegrationTest {

        @Autowired
        ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        OutboxRepository outboxRepository;

        @BeforeEach
        void setUp(){

        }

        @Transactional
        public void publishReservationSuccessEvent(ReservationSuccessEvent event){
                applicationEventPublisher.publishEvent(event);
        }

        @Transactional
        public void publishPaymentSuccessEvent(PaymentSuccessEvent event){
                applicationEventPublisher.publishEvent(event);
        }

        @Test
        @DisplayName("좌석 예약 성공 이벤트 발생 시 outbox가 제대로 저장되는지 테스트")
        public void 좌석_예약_성공_이벤트_발행_시_status가_published인_outbox_정보가_outbox_테이블에_정확하게_있어야_한다() throws JsonProcessingException {

                ObjectMapper objectMapper = new ObjectMapper();

                ReservationSuccessEvent event = ReservationSuccessEvent.builder()
                                                        .reservationId(1L)
                                                        .seatId(2L)
                                                        .userId("tester")
                                                        .paymentId(3L)
                                                        .reservationStatus(ReservationStatusType.TEMP.ordinal())
                                                        .build();


                publishReservationSuccessEvent(event);


                Outbox sample = Outbox.builder()
                        .aggregateType(DomainType.RESERVATION)
                        .aggregateId(event.getReservationId())
                        .eventType(KafkaTopicKey.RESERVATION_SUCCESS_EVENT)
                        .payload(objectMapper.writeValueAsString(event))
                        .status(OutboxStatusType.PUBLISHED)
                        .build();

                Outbox outbox = outboxRepository.findOutboxByOutbox(sample).orElseThrow();

                Assertions.assertThat(outbox).isEqualTo(sample);

        }




        @Test
        @DisplayName("결제 성공 이벤트 발생 시 outbox가 제대로 저장되는지 테스트")
        public void 결제_성공_이벤트_발행_시_status가_published인_outbox_정보가_outbox_테이블에_정확하게_있어야_한다() throws JsonProcessingException {

                ObjectMapper objectMapper = new ObjectMapper();

                PaymentSuccessEvent event = PaymentSuccessEvent.builder()
                        .paymentId(1L)
                        .userId("tester")
                        .paymentStatus(PaymentStatusType.PAID)
                        .totalPrice(3000L)
                        .build();


                publishPaymentSuccessEvent(event);


                Outbox sample = Outbox.builder()
                        .aggregateType(DomainType.PAYMENT)
                        .aggregateId(event.getPaymentId())
                        .eventType(KafkaTopicKey.PAYMENT_SUCCESS_EVENT)
                        .payload(objectMapper.writeValueAsString(event))
                        .status(OutboxStatusType.PUBLISHED)
                        .build();

                Outbox outbox = outboxRepository.findOutboxByOutbox(sample).orElseThrow();

                Assertions.assertThat(outbox).isEqualTo(sample);

        }

}
