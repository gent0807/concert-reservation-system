package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenStatusType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TokenFacadeDTOResult(
        Long tokenId,
        String userId,
        TokenStatusType tokenStatus,
        LocalDateTime expiredAt
) {
}
