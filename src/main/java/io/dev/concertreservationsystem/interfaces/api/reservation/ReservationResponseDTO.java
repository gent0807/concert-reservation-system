package io.dev.concertreservationsystem.interfaces.api.reservation;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponseDTO(
        Long reservationId,
        String userId,
        Long seatId,
        Long paymentId,
        Integer reservationStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
