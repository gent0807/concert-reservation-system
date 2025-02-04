package io.dev.concertreservationsystem.common.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisCacheConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory defaultRedisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    /**
     * RedisTemplate 설정하여 캐싱하면, 세밀한 캐시 데이터 관리 가능
     * @param
     * @return RedisTemplate<String, String>
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(defaultRedisConnectionFactory());
        return redisTemplate;
    }

    /**
     * CacheManager 인터페이스를 이용한 구현체 RedisCacheManager 설정, Annotation 이용한 캐싱 Aspect Oriented Programming 가능
     * @param
     * @return RedisCacheManager
     * cacheManagerName : defaultRedisCacheManager
     */
    @Primary
    @Bean
    public RedisCacheManager defaultRedisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .computePrefixWith(CacheKeyPrefix.simple())   // "cache::key"와 같이 key 앞에 '::'를 삽입
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // "tokens" 캐시 설정
        RedisCacheConfiguration tokenCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // "users" 캐시 설정
        RedisCacheConfiguration userCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // "concertDetails" 캐시 설정
        RedisCacheConfiguration concertDetailCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // "seats" 캐시 설정
        RedisCacheConfiguration seatCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // "reservations" 캐시 설정
        RedisCacheConfiguration reservationCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // "payments" 캐시 설정
        RedisCacheConfiguration paymentCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));

        // 각 캐시에 대한 설정을 매핑
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheKey.TOKEN_CACHE_NAME, tokenCacheConfiguration);
        cacheConfigurations.put(CacheKey.USER_CACHE_NAME, userCacheConfiguration);
        cacheConfigurations.put(CacheKey.CONCERT_DETAIL_CACHE_NAME, concertDetailCacheConfiguration);
        cacheConfigurations.put(CacheKey.SEAT_CACHE_NAME, seatCacheConfiguration);
        cacheConfigurations.put(CacheKey.RESERVATION_CACHE_NAME, reservationCacheConfiguration);
        cacheConfigurations.put(CacheKey.PAYMENT_CACHE_NAME, paymentCacheConfiguration);

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(defaultRedisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

}

