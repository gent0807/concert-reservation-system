package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentDTOResult(
        Long paymentId,
        PaymentStatusType paymentStatus,
        Integer totalPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public ConcertReserveAdminDTOResult convertToConcertReserveAdminDTOResult() {
        return ConcertReserveAdminDTOResult.builder()
                .paymentDTOResult(this)
                .build();
    }
}
