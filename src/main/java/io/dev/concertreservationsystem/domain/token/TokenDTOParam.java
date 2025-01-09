package io.dev.concertreservationsystem.domain.token;

import lombok.Builder;

@Builder
public record TokenDTOParam(
        Long tokenId,
        String userId
) {
}
