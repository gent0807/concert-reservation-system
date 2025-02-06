package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.domain.token.Token;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public interface RedisTokenRepository {
    Double saveWaitingToken(String userId);

    Set<String> findWaitingTokenByScore(Double score);

    Set<ZSetOperations.TypedTuple<String>> getActiveTokens(long maxActiveTokenLimit);

    void saveActiveToken(ZSetOperations.TypedTuple<String> tokenId);

    String findActiveUserByTokenId(String tokenId);
}
