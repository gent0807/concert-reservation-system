package io.dev.concertreservationsystem.interfaces.api.reservation;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long reservationId,
        Long seatId,
        Integer reservationStatusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
