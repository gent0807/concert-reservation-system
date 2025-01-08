package io.dev.concertreservationsystem.interfaces.api.reservation;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponseDTO(
        Long reservationId,
        Long seatId,
        Integer reservationStatusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
