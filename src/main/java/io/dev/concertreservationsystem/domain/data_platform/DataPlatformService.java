package io.dev.concertreservationsystem.domain.data_platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.concertreservationsystem.common.config.kafka.KafkaTopicKey;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.domain.common.key.DomainType;
import io.dev.concertreservationsystem.domain.outbox.Outbox;
import io.dev.concertreservationsystem.domain.outbox.OutboxRepository;
import io.dev.concertreservationsystem.domain.outbox.OutboxStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentSuccessEvent;
import io.dev.concertreservationsystem.domain.reservation.ReservationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataPlatformService {

    private final DataPlatformRepository dataPlatformRepository;

    private final OutboxRepository outboxRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public void sendReservationSuccessData(String message) throws JsonProcessingException {

        ReservationSuccessEvent reservationSuccessEvent = objectMapper.readValue(message, ReservationSuccessEvent.class);

        // Outbox 테이블에 존재하는지 검사
        Outbox outbox = outboxRepository.findOutboxByOutbox(Outbox.builder()
                                                                .aggregateType(DomainType.RESERVATION)
                                                                .aggregateId(reservationSuccessEvent.getReservationId())
                                                                .eventType(KafkaTopicKey.RESERVATION_SUCCESS_EVENT)
                                                                .payload(message)
                                                                .build()).orElseThrow(()->{
                                                                    throw new ServiceDataNotFoundException(ErrorCode.OUTBOX_NOT_FOUND, "DATAPLATFORM SERVICE", "sendReservationSuccessData");
                                                                });

        // Outbox 테이블에서 아직 상태가 초기 상태면, 좌석 예약 정보 전송
        if(outbox.getStatus() == OutboxStatusType.INIT){
            dataPlatformRepository.sendData(message) ;

            outbox.setStatus(OutboxStatusType.PUBLISHED);

            outboxRepository.save(outbox);
        }


    }

    @Transactional
    public void sendPaymentSuccessData(String message) throws JsonProcessingException {

        PaymentSuccessEvent paymentSuccessEvent = objectMapper.readValue(message, PaymentSuccessEvent.class);

        Outbox outbox = outboxRepository.findOutboxByOutbox(Outbox.builder()
                                                                .aggregateType(DomainType.PAYMENT)
                                                                .aggregateId(paymentSuccessEvent.getPaymentId())
                                                                .eventType(KafkaTopicKey.PAYMENT_SUCCESS_EVENT)
                                                                .payload(message)
                                                                .build()).orElseThrow(()->{
                                                                            throw new ServiceDataNotFoundException(ErrorCode.OUTBOX_NOT_FOUND, "DATAPLATFORM SERVICE", "sendPaymentSuccessData");
                                                                });

        // Outbox 테이블에 아직 상태가 초기 상태면, 결제 정보 전송
        if(outbox.getStatus() == OutboxStatusType.INIT){
            dataPlatformRepository.sendData(message) ;

            outbox.setStatus(OutboxStatusType.PUBLISHED);

            outboxRepository.save(outbox);
        }
    }
}
