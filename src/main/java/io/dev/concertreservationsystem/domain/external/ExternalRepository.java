package io.dev.concertreservationsystem.domain.external;

import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;

public interface ExternalRepository {
    public void sendPaymentData(PaymentDTOResult paymentDTOResult) ;
}
