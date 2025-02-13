package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class RedisToken implements Serializable {
    private String tokenId;
    private String userId;

    public TokenDTOResult convertToTokenDTOResult() {
        return TokenDTOResult.builder()
                .tokenId(this.tokenId)
                .userId(this.userId)
                .build();
    }
}
