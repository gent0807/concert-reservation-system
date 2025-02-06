package io.dev.concertreservationsystem.redis.token;


import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.application.token.TokenFacade;
import io.dev.concertreservationsystem.application.token.TokenFacadeDTOParam;
import io.dev.concertreservationsystem.application.token.TokenFacadeDTOResult;
import io.dev.concertreservationsystem.common.config.redis.CacheKey;
import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenRepository;
import io.dev.concertreservationsystem.domain.token.redis.RedisTokenRepository;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("redis")
@Slf4j
public class TokenFacadeTest {

    @Autowired
    TokenFacade tokenFacade;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTokenRepository redisTokenRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final String TEST_USER_ID = UUID.randomUUID().toString();

    private static final String TEST_USER_NAME = "TESTER";

    private static final int TEST_AGE = 31;

    private static final long USER_INIT_POINT = 10000L;

    User saveUser;

    List<String> saveTokenIdList = new ArrayList<>();

    @BeforeEach
    void setUp(){

        // User 저장
        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName(TEST_USER_NAME)
                .gender(UserGenderType.MALE)
                .age(TEST_AGE)
                .point(USER_INIT_POINT)
                .build();

        userRepository.save(user);

        saveUser = userRepository.findUserByUserId(user.getUserId()).orElseThrow(()->{
            log.debug("finding user of user-id [{}] is fail", user.getUserId());
            return null;
        });

        // 저장된 User의 userId롤 이용한 접속을 가장하여 대기열 토큰 20개 생성
        for(int i = 0; i < 20; i++){
            Double score = redisTokenRepository.saveWaitingToken(saveUser.getUserId());

            String saveTokenId = redisTokenRepository.findWaitingTokenByScore(score).stream().findFirst().orElseThrow(()->{
                log.debug("finding token of token-id [{}] is fail", score);
                return null;
            });

            saveTokenIdList.add(saveTokenId);
        }
    }

    @Test
    @DisplayName("redis를_이용해_대기열_토큰을_하나_발급할_시_결과적으로_redis에_저장된_결과_정보와_요청에_담겼던_정보가_일치해야_한다")
    public void redis를_이용해_대기열_토큰을_하나_발급할_시_결과적으로_redis에_저장된_결과_정보와_요청에_담겼던_정보가_일치해야_한다(){

        long startTime;

        long endTime;

        // 대기열 토큰 발급 요청 DTO 생성
        TokenFacadeDTOParam tokenFacadeDTOParam = TokenFacadeDTOParam.builder().userId(saveUser.getUserId()).build();

        startTime = System.currentTimeMillis();

        TokenFacadeDTOResult tokenFacadeDTOResult = tokenFacade.publishToken(tokenFacadeDTOParam);

        endTime = System.currentTimeMillis();

        assertThat(tokenFacadeDTOResult.userId()).isEqualTo(tokenFacadeDTOParam.userId());
        //assertThat(tokenFacadeDTOResult.tokenId()).isEqualTo(redisTemplate.opsForZSet().popMin(CacheKey.WAITING_TOKEN_CACHE_NAME, 1).stream().findFirst().get().getValue());
        assertThat(tokenFacadeDTOResult.userId()).isEqualTo(redisTemplate.opsForHash().get(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenFacadeDTOResult.tokenId(), "userId"));

        log.debug("실행 시간 : {} ms", endTime - startTime);

    }

    @Test
    @DisplayName("redis를_이용해_특정_유저_id로_동시에_여러_번_대기열_토큰을_발급_시도할_시_발급_시도_횟수만큼_대기열_토큰이_추가돼있어야_한다")
    public void redis를_이용해_특정_유저_id로_동시에_여러_번_대기열_토큰을_발급_시도할_시_발급_시도_횟수만큼_대기열_토큰이_추가돼있어야_한다() throws InterruptedException {

        long startTime;

        long endTime;

        // 쓰레드 설정
        int threadCount = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 대기열 토큰 발급 요청 DTO 생성
        TokenFacadeDTOParam tokenFacadeDTOParam = TokenFacadeDTOParam.builder().userId(saveUser.getUserId()).build();

        // 동시 실행 결과를 저장할 리스트
        List<Future<Boolean>> results = new ArrayList<>();

        for(int i = 0; i < threadCount; i++){
            results.add(executorService.submit(()->{
                try{
                    latch.countDown();
                    latch.await();

                    tokenFacade.publishToken(tokenFacadeDTOParam);

                    return true;

                }catch (Exception e) {
                    return false;
                }

            }));
        }
        startTime = System.currentTimeMillis();

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        endTime = System.currentTimeMillis();

        // 결과 확인
        long successCount = results.stream().filter(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                return false;
            }
        }).count();

        assertThat(threadCount).isEqualTo(successCount);

        log.debug("실행 시간 : {} ms", endTime - startTime);
    }


}
