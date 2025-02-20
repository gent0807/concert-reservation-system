package io.dev.concertreservationsystem.interfaces.event.consumer.payment;

import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.domain.data_platform.DataPlatformService;
import io.dev.concertreservationsystem.domain.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentService paymentService;

    private final DataPlatformService dataPlatformService;

    @KafkaListener(topics = KafkaTopicKey.PAYMENT_CREATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void createPayment(@Payload String message, Acknowledgment acknowledgment){

    }

    @KafkaListener(topics = KafkaTopicKey.PAYMENT_STATUS_UPDATE_EVENT, groupId = "${spring.kafka.consumer.group-id}")
    public void updatePaymentStatus(@Payload String message, Acknowledgment acknowledgment){

    }

    @KafkaListener(topics = KafkaTopicKey.PAYMENT_SUCCESS_EVENT, groupId =  "${spring.kafka.consumer.group-id}")
    public void sendPaymentSuccessToDataPlatform(@Payload String message, Acknowledgment acknowledgment){
        dataPlatformService.sendData(message);
    }

}
