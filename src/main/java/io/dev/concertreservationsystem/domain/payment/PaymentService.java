package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.domain.payment.factory.NotPaidPaymentFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final NotPaidPaymentFactory notPaidPaymentFactory;

    public PaymentDTOResult publishNewPayment() {
        // Payment 타입 객체 생성
        Payment payment = notPaidPaymentFactory.orderPayment();

        paymentRepository.savePayment(payment);

        return paymentRepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(payment.getPaymentStatus()).orElseThrow(()->{
            throw new PaymentNotFoundException(ErrorCode.PAYMENT_SAVE_FAILED);
        }).get(0).convertToPaymentDTOResult();


    }
}
