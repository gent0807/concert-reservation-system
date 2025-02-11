package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.common.config.redis.CacheKey;
import io.dev.concertreservationsystem.domain.token.redis.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Double saveWaitingToken(String userId) {

        long score = System.currentTimeMillis();

        String tokenId = UUID.randomUUID().toString();

        redisTemplate.opsForZSet().add(CacheKey.WAITING_TOKEN_CACHE_NAME, tokenId, score);

        redisTemplate.opsForHash().put(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenId, "userId", userId);

        redisTemplate.expire(CacheKey.WAITING_TOKEN_CACHE_NAME, 11, TimeUnit.MINUTES);

        redisTemplate.expire(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenId, 11, TimeUnit.MINUTES);

        return (double) score;
    }

    @Override
    public Set<String> findWaitingTokenByScore(Double score) {
        return redisTemplate.opsForZSet().rangeByScore(CacheKey.WAITING_TOKEN_CACHE_NAME, score, score);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getActiveTokens(long maxActiveTokenLimit) {
        return redisTemplate.opsForZSet().popMin(CacheKey.WAITING_TOKEN_CACHE_NAME, maxActiveTokenLimit);
    }

    @Override
    public void saveActiveToken(ZSetOperations.TypedTuple<String> token) {
        redisTemplate.opsForHash().put(CacheKey.ACTIVE_TOKENS_CACHE_NAME + "::" + token.getValue(), "userId", redisTemplate.opsForHash().get(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + token.getValue(), "userId"));
        redisTemplate.opsForHash().delete(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + token.getValue());
        redisTemplate.expire(CacheKey.ACTIVE_TOKENS_CACHE_NAME + "::" + token.getValue(), 10, TimeUnit.MINUTES);
    }

    @Override
    public String findActiveUserByTokenId(String tokenId) {
        return String.valueOf(redisTemplate.opsForHash().get(CacheKey.ACTIVE_TOKENS_CACHE_NAME + "::" + tokenId, "userId"));
    }

}
