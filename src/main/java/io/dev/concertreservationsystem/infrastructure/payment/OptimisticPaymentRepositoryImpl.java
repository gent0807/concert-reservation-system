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
@Profile(value = "optimistic-lock")
public class OptimisticPaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJPARepository optimisticPaymentJPARepository;

    @Override
    public void save(Payment payment){
        optimisticPaymentJPARepository.save(payment);
    }

    @Override
    public Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatusType){
        return optimisticPaymentJPARepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(paymentStatusType);
    }

    @Override
    public Optional<Payment> findPaymentByPaymentIdWithLock(Long paymentId){
        return optimisticPaymentJPARepository.findPaymentByPaymentIdForUpdateWithOptimisticLock(paymentId);
    }
}
