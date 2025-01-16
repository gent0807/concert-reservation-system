package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.application.token.TokenFacadeDTOResult;
import lombok.Builder;

@Builder
public record TokenDTOResult(
        Long tokenId,
        String userId,
        TokenStatusType tokenStatus
) {
    public TokenFacadeDTOResult convertToTokenFacadeDTOResult() {
        return null;
    }
}
