package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.application.token.TokenAdminDTOResult;
import lombok.Builder;

@Builder
public record TokenDTOResult(
        Long tokenId,
        String userId,
        TokenStatusType tokenStatus
) {
    public TokenAdminDTOResult convertToTokenAdminDTOResult() {
        return null;
    }
}
