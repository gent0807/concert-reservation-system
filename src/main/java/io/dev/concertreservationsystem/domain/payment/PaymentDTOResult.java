package io.dev.concertreservationsystem.domain.payment;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentDTOResult(
        Long paymentId,
        PaymentStatusType paymentStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
