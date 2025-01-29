package io.dev.concertreservationsystem.infrastructure.payment;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "pessimistic-lock")
public class PessimisticPaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJPARepository pessimisticPaymentJPARepository;

    @Override
    public void save(Payment payment){
        pessimisticPaymentJPARepository.save(payment);
    }

    @Override
    public Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatusType){
        return pessimisticPaymentJPARepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(paymentStatusType);
    }

    @Override
    public Optional<Payment> findPaymentByPaymentIdWithLock(Long paymentId){
        return pessimisticPaymentJPARepository.findPaymentByPaymentIdForUpdateWithPessimisticLock(paymentId);
    }
}
