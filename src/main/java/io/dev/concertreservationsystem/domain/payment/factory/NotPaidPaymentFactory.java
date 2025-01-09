package io.dev.concertreservationsystem.domain.payment.factory;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import org.springframework.stereotype.Component;

@Component
public class NotPaidPaymentFactory extends PaymentFactory{

    @Override
    public Payment createPayment(){
        return Payment.builder()
                .paymentStatus(PaymentStatusType.NOT_PAID)
                .build();
    }
}
