package io.dev.concertreservationsystem.domain.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);

    Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatusType);

    Optional<Payment> findPaymentByPaymentIdWithLock(Long paymentId);
}
