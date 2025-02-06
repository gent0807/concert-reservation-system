package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenDTOResult;

import java.util.Set;

public interface RedisTokenRepository {
    Double saveToken(Token token);

    Set<Token> findTokenByTokenId(Double tokenId);
}
