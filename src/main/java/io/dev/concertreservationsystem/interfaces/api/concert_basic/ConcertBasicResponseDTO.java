package io.dev.concertreservationsystem.interfaces.api.concert_basic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ConcertBasicResponseDTO(
        @JsonProperty("concertBasicId") Long concertBasicId,
        @JsonProperty("concertName") String concertName,
        @JsonProperty("genreId") Long genreId,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
}
