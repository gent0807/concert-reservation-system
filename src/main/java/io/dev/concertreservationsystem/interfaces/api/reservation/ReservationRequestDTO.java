package io.dev.concertreservationsystem.interfaces.api.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
        @JsonProperty("reservationId") Long reservationId,
        @JsonProperty("seatId") Long seatId,
        @JsonProperty("reservationStatusId") Integer reservationStatusId,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
}
