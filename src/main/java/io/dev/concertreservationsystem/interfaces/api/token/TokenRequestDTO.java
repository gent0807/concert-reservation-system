package io.dev.concertreservationsystem.interfaces.api.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TokenRequestDTO(
        @JsonProperty("tokenId") Integer tokenId,
        @JsonProperty("memberId") String memberId,
        @JsonProperty("tokenStatusId") Integer tokenStatusId,
        @JsonProperty("expiredAt")LocalDateTime expiredAt
) {
}
