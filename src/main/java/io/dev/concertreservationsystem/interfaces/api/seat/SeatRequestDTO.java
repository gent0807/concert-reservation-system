package io.dev.concertreservationsystem.interfaces.api.seat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SeatRequestDTO(
        @JsonProperty("seatId") Long seatId,
        @JsonProperty("concertDetailId") Long concertDetailId,
        @JsonProperty("seatNumber") Long seatNumber,
        @JsonProperty("seatStatusId") Integer seatStatusId,
        @JsonProperty("price") Integer price,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
){

}
