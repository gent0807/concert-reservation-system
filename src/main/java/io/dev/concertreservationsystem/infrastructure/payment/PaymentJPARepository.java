package io.dev.concertreservationsystem.infrastructure.payment;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentJPARepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType paymentStatus);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT p from Payment p WHERE p.paymentId = :paymentId")
    Optional<Payment> findPaymentByPaymentIdForShareWithPessimisticLock(@Param("paymentId") Long paymentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p from Payment p WHERE p.paymentId = :paymentId")
    Optional<Payment> findPaymentByPaymentIdForUpdateWithPessimisticLock(@Param("paymentId") Long paymentId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "SELECT p from Payment p WHERE p.paymentId = :paymentId")
    Optional<Payment> findPaymentByPaymentIdForUpdateWithOptimisticLock(@Param("paymentId") Long paymentId);
}
