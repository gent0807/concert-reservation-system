package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOParam;
import lombok.Builder;

@Builder
public record TokenAdminDTOParam(
        Long tokenId,
        String userId
) {
    public TokenDTOParam convertToTokenDTOParam() {
        return TokenDTOParam.builder()
                .tokenId(tokenId)
                .userId(userId)
                .build();
    }


}
