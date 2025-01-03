package io.dev.concertreservationsystem.interfaces.api.concert_detail;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ConcertDetailRequestDTO(
        @JsonProperty("concertDetailId") Long concertDetailId,
        @JsonProperty("concertBasicId") Long concertBasicId,
        @JsonProperty("concertDetailStatusId") Long concertDetailStatusId,
        @JsonProperty("startDate") LocalDateTime startDate,
        @JsonProperty("startDate") LocalDateTime endDate,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
        ){
}
