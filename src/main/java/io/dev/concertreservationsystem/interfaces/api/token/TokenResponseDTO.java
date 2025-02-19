package io.dev.concertreservationsystem.interfaces.api.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TokenResponseDTO(
        Integer tokenId,
        String userId,
        Integer tokenStatusId,
        LocalDateTime expiredAt
){
}
