package io.dev.concertreservationsystem.infrastructure.payment;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
        private final PaymentJPARepository paymentJPARepository;

        @Override
        public void savePayment(Payment payment){
                paymentJPARepository.save(payment);
        }

        @Override
        public Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatusType){
                return paymentJPARepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(paymentStatusType);
        }

        @Override
        public Optional<Payment> findPaymentByPaymentId(Long paymentId){
                return paymentJPARepository.findPaymentByPaymentId(paymentId);
        }
}
