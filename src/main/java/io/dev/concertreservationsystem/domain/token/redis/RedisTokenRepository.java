package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.domain.token.Token;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public interface RedisTokenRepository {
    Double saveWaitingToken(Token token);

    Set<Token> findWaitingTokenByTokenId(Double tokenId);

    Set<ZSetOperations.TypedTuple<Token>> getActiveTokens(long maxActiveTokenLimit);

    void saveActiveToken(ZSetOperations.TypedTuple<Token> token);

    String findActiveUserByTokenId(Long tokenId);
}
