package io.dev.concertreservationsystem.domain.payment.factory;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentInvalidException;

public abstract class PaymentFactory {
    public final Payment orderPayment(Integer totalPrice) {

        checkPaymentValidationOnBuild(totalPrice);

        Payment payment = createPayment(totalPrice);

        return payment;
    }

    public void checkPaymentValidationOnBuild(Integer totalPrice){
        checkTotalPriceValidation(totalPrice);

    }

    private static void checkTotalPriceValidation(Integer totalPrice) {
        if(totalPrice == null || totalPrice < 0){
            throw new PaymentInvalidException(ErrorCode.PAYMENT_TOTAL_PRICE_INVALID);
        }
    }


    protected abstract Payment createPayment(Integer totalPrice);

}
