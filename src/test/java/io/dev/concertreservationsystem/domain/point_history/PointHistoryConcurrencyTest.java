package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("default")
@Slf4j
public class PointHistoryConcurrencyTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointHistoryService pointHistoryService;

    private static final String TEST_USER_ID = UUID.randomUUID().toString(); // 테스트용 유저 ID

    private static final String TEST_USER_NAME = "TESTER";

    private static final int TEST_USER_AGE = 31;

    private static final long TEST_USER_INIT_POINT = 10000L;

    User saveUser;

    @BeforeEach
    void setUp(){

        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName(TEST_USER_NAME)
                .gender(UserGenderType.MALE)
                .age(TEST_USER_AGE)
                .point(TEST_USER_INIT_POINT)
                .build();

        userRepository.save(user);

        saveUser = userRepository.findUserByUserId(user.getUserId()).orElseThrow(()->{
            log.debug("finding user of user-id [{}] is fail", user.getUserId() );
            return null;
        });


    }

    @Test
    @DisplayName("동일한 userId로 여러 개의 포인트 충전 요청이 동시에 들어오는 경우  낙관적 락 사용 시엔 한 번을 성공해야 하고 비관적 락 사용 시엔 정확한 값으로 계산되어야 한다")
    public void 동일한_userId로_여러_개의_포인트_충전_요청이_동시에_들어오는_경우_낙관적_락_사용_시엔_한_번을_성공해야_하고_비관적_락_사용_시엔_정확한_값으로_계산되어야_한다() throws InterruptedException {

        long startTime;

        long endTime;

        // 쓰레드 설정
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);


        // 각 쓰레드에서 충전할 포인트
        long chargePointPerThread = 10000L;

        // 포인트 충전 DTO 생성
        PointHistoryDTOParam pointHistoryDTOParam = PointHistoryDTOParam.builder()
                .userId(saveUser.getUserId())
                .type(PointTransactionType.CHARGE)
                .amount(chargePointPerThread)
                .build();

        // 동시 실행 결과를 저장할 리스트
        List<Future<Boolean>> results = new ArrayList<>();

        for(int i = 0; i < threadCount; i++){
            results.add(executorService.submit(()->{
                try{
                    latch.countDown();
                    latch.await();

                    // 서비스에서 포인트 충전
                    pointHistoryService.insertChargeUserPointHistory(pointHistoryDTOParam);

                    return true;
                }catch (DomainModelParamInvalidException e){

                    return false;
                }catch (ServiceDataNotFoundException e){

                    return false;
                }catch (ObjectOptimisticLockingFailureException e){

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

        long resultUserPoint = TEST_USER_INIT_POINT + (successCount * chargePointPerThread);

        User user = userRepository.findUserByUserId(saveUser.getUserId()).orElseThrow();

        log.debug("유저 포인트 : {}", user.getPoint());

        log.debug("포인트 충전 내역 결과 포인트: {}", resultUserPoint);

        Assertions.assertThat(user.getPoint()).isEqualTo(resultUserPoint);

        log.debug("실행 시간 : {} ms", endTime - startTime);
    }
}
