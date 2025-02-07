package io.dev.concertreservationsystem.redis.token;


import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.application.token.TokenFacade;
import io.dev.concertreservationsystem.application.token.TokenFacadeDTOParam;
import io.dev.concertreservationsystem.application.token.TokenFacadeDTOResult;
import io.dev.concertreservationsystem.common.config.redis.CacheKey;
import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenDTOParam;
import io.dev.concertreservationsystem.domain.token.TokenRepository;
import io.dev.concertreservationsystem.domain.token.redis.RedisToken;
import io.dev.concertreservationsystem.domain.token.redis.RedisTokenRepository;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

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

    long addCount = 20;

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
        for(int i = 0; i < addCount; i++){
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

    @Test
    @DisplayName("대기열_토큰을_활성화_함으로써_삭제된_대기열_토큰들이_각각_활성화_토큰과_정보가_같아야_한다")
    public void 대기열_토큰을_활성화_함으로써_삭제된_대기열_토큰들이_각각_활성_상태의_토큰과_정보가_같아야_한다() {
        long startTime;

        long endTime;

        // 한 번 활성화할 때 활성화시킬 토큰 개수
        long activateCount = addCount - 1 ;

        // 활성화시킬 예정인 대기열 토큰 목록 가져오기
        Set<String> waiting_tokens_to_activate = redisTemplate.opsForZSet().range(CacheKey.WAITING_TOKEN_CACHE_NAME, 0, activateCount - 1);

        // 활성화시킬 대기열 토큰들의 정보를 담을 activate_token_list
        List<RedisToken> activate_token_list = new ArrayList<>();

        // activate_token_list에 활성화 예정 대기열 토큰 정보 add
        waiting_tokens_to_activate.stream().forEach(tokenId -> {

           String userId = String.valueOf(redisTemplate.opsForHash().get(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenId, "userId"));

           activate_token_list.add(RedisToken.builder().tokenId(tokenId).userId(userId).build());

        });

        startTime = System.currentTimeMillis();

        tokenFacade.activeTokens(activateCount);

        endTime = System.currentTimeMillis();

        activate_token_list.stream().forEach(activate_token -> {

            // 실제 활성화된 토큰의 유저 아이디와 해당 토큰이 활성화됐을 경우 예상했던 유저 아이디가 일치하는지 확인한다.
            assertThat(activate_token.getUserId()).isEqualTo(redisTemplate.opsForHash().get(CacheKey.ACTIVE_TOKENS_CACHE_NAME + "::" + activate_token.getTokenId(), "userId"));

            // 토큰이 활성화되기 전 대기열 토큰일 때 해쉬에 저장해둔 해당 대기열 토큰의 유저 아이디가 여전히 존재하면 안 된다. 이를 확인한다.
            assertThat(redisTemplate.opsForHash().get(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + activate_token.getTokenId(), "userId")).isNull();
        });

        log.debug("실행 시간 : {} ms", endTime - startTime);
    }

    @Test
    @DisplayName("대기열_토큰을_2개_생성하여_하나는_활성화_시키고_하나는_그대로_대기_상태로_두었을_때_정확하게_활성_상태인_토큰과_대기_상태인_토큰을_구분할_수_있어야_한다")
    public void 대기열_토큰을_2개_생성하여_하나는_활성화_시키고_하나는_그대로_대기_상태로_두었을_때_정확하게_활성_상태인_토큰과_대기_상태인_토큰을_구분할_수_있어야_한다() {
        long startTime;

        long endTime;

        // 대기 상태로 둘 대기열 토큰의 토큰 아이디
        String tokenIdForWait = UUID.randomUUID().toString();

        // 대기 상태로 둘 대기열 토큰의 유저 아이디
        String tokenIdForActive = UUID.randomUUID().toString();

        // 활성 상태로 전환할 대기열 토큰의 토큰 아이디
        String userIdForWait = UUID.randomUUID().toString();

        // 활성 상태로 전환할 대기열 토큰의 유저 아이디
        String userIdForActive = UUID.randomUUID().toString();

        TokenFacadeDTOParam tokenDTOParamForWait = TokenFacadeDTOParam.builder().tokenId(tokenIdForWait).userId(userIdForWait).build();

        TokenFacadeDTOParam tokenDTOParamForActive = TokenFacadeDTOParam.builder().tokenId(tokenIdForActive).userId(userIdForActive).build();


        // 대기 상태로 둘 대기열 토큰의 토큰 아이디 저장
        redisTemplate.opsForZSet().addIfAbsent(CacheKey.WAITING_TOKEN_CACHE_NAME, tokenIdForWait, System.currentTimeMillis());
        // 대기 상태로 둘 대기열 토큰의 유저 아이디 저장
        redisTemplate.opsForHash().put(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenIdForWait, "userId", userIdForWait);

        // 활성 상태로 전환할 대기열 토큰의 토큰 아이디 저장
        redisTemplate.opsForZSet().addIfAbsent(CacheKey.WAITING_TOKEN_CACHE_NAME, tokenIdForActive, System.currentTimeMillis());
        // 활성 상태로 전환할 대기열 토큰의 유저 아이디 저장
        redisTemplate.opsForHash().put(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenIdForActive, "userId", userIdForActive);

        
        // 활성 상태로 전환할 대기열 토큰의 토큰 아이디 대기열 토큰 저장 공간으로부터 제거
        redisTemplate.opsForZSet().remove(CacheKey.WAITING_TOKEN_CACHE_NAME, tokenIdForActive);
        // 활성 상태로 전환할 대기열 토큰의 유저 아이디 대기열 토큰 저장 공간으로부터 제거
        redisTemplate.opsForHash().delete(CacheKey.WAITING_TOKEN_CACHE_NAME + "::" + tokenIdForActive);

        // 활성 상태로 전환할 대기열 토큰 실제로 활성 상태로 전환하여 저장
        redisTemplate.opsForHash().put(CacheKey.ACTIVE_TOKENS_CACHE_NAME + "::" + tokenIdForActive, "userId", userIdForActive);

        Assert.assertThrows(ServiceDataNotFoundException.class, () -> {
            tokenFacade.checkTokenStatusValidation(tokenDTOParamForWait);
        });

        assertThatNoException().isThrownBy(() -> {
            tokenFacade.checkTokenStatusValidation(tokenDTOParamForActive);
        });
    }
}
