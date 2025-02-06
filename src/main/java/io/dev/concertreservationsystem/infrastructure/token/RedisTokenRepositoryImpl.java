package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.common.config.redis.CacheKey;
import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import io.dev.concertreservationsystem.domain.token.TokenRepository;
import io.dev.concertreservationsystem.domain.token.redis.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepositoryImpl implements RedisTokenRepository {

    private final RedisTemplate<String, Token> redisTemplate;


    @Override
    public Double saveToken(Token token) {
        Double tokenId = redisTemplate.opsForZSet().incrementScore(CacheKey.WAITING_TOKEN_CACHE_NAME, token, 1);

        redisTemplate.expire(CacheKey.WAITING_TOKEN_CACHE_NAME, 11, TimeUnit.MINUTES);

        return tokenId;
    }

    @Override
    public Set<Token> findTokenByTokenId(Double tokenId) {
        return redisTemplate.opsForZSet().rangeByScore(CacheKey.WAITING_TOKEN_CACHE_NAME, tokenId, tokenId);
    }
}
