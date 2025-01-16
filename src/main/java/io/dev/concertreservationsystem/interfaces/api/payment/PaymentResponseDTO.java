package io.dev.concertreservationsystem.interfaces.api.payment;

import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentResponseDTO (
        Long paymentId,
        PaymentStatusType paymentStatus,
        Integer totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
