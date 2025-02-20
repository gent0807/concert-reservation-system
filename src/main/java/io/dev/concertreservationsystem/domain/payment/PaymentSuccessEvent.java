package io.dev.concertreservationsystem.domain.payment;

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
