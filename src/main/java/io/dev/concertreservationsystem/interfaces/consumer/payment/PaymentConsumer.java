package io.dev.concertreservationsystem.interfaces.consumer.payment;

import io.dev.concertreservationsystem.common.config.kafka.KafkaKey;
import io.dev.concertreservationsystem.domain.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaKey.PAYMENT_CREATE_EVENT, groupId = KafkaKey.CONSUMER_GROUP_ID)
    public void createPayment(String kafkaMessage){

    }

    @KafkaListener(topics = KafkaKey.PAYMENT_STATUS_UPDATE_EVENT, groupId = KafkaKey.CONSUMER_GROUP_ID)
    public void updatePaymentStatus(String kafkaMessage){

    }
}
