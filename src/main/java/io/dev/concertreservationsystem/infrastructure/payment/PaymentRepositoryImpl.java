package io.dev.concertreservationsystem.infrastructure.payment;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Profile(value = {"default", "redis"})
public class PaymentRepositoryImpl implements PaymentRepository {
        private final PaymentJPARepository paymentJPARepository;

        @Override
        public void save(Payment payment){
                paymentJPARepository.save(payment);
        }

        @Override
        public Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatusType){
                return paymentJPARepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(paymentStatusType);
        }

        @Override
        public Optional<Payment> findPaymentByPaymentIdWithLock(Long paymentId){
                return paymentJPARepository.findPaymentByPaymentIdForUpdateWithPessimisticLock(paymentId);
        }
}
