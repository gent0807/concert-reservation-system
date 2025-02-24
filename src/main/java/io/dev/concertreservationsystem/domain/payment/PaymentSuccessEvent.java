package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.domain.outbox.OutboxDTOParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentSuccessEvent {
    private Long paymentId;

    private String userId;

    private PaymentStatusType paymentStatus;

    private Long totalPrice;

}
