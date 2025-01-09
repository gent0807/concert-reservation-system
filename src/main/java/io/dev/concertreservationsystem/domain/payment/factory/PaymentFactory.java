package io.dev.concertreservationsystem.domain.payment.factory;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.payment.Payment;

public abstract class PaymentFactory {
    public final Payment orderPayment() {

        checkPaymentValidationOnBuild();

        Payment payment = createPayment();

        return payment;
    }

    public void checkPaymentValidationOnBuild(){

    }




    protected abstract Payment createPayment();

}
