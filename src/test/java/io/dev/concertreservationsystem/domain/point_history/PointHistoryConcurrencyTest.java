package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest
@Testcontainers
@Slf4j
public class PointHistoryConcurrencyTest {
    @Autowired
    private PointHistoryService pointHistoryService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_USER_ID = "tid"; // 테스트용 유저 ID

    private static final long USER_INIT_POINT = 10000L;

    User saveUser;

    @BeforeEach
    void setUp(){

        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName("tester")
                .gender(UserGenderType.MALE)
                .age(31)
                .point(USER_INIT_POINT)
                .build();

        log.info("user id : {}", user.getUserId());

        userRepository.saveUser(user);

        saveUser = userRepository.findUserByUserId(user.getUserId()).orElseThrow(()->{
            log.info("find user fail in id({})", user.getUserId() );
            return null;
        });

        log.info("save user id : {}", saveUser.getUserId());

    }

    @Test
    public void 동일한_userId로_여러_개의_포인트_충전_요청이_동시에_들어오는_경우_정확한_값으로_계산되어야_한다() throws InterruptedException {

        log.info("save user id : {}", saveUser.getUserId());

        long startTime;

        long endTime;

        // 쓰레드 설정
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);


        // 각 쓰레드에서 충전할 포인트
        int chargePointPerThread = 10000;

        // 포인트 충전 DTO 생성
        PointHistoryDTOParam pointHistoryDTOParam = PointHistoryDTOParam.builder()
                .userId(saveUser.getUserId())
                .type(PointTransactionType.CHARGE)
                .amount(chargePointPerThread)
                .build();

        log.info("pointHistoryDTOParam user id : {}", pointHistoryDTOParam.userId());

        // 동시 실행 결과를 저장할 리스트
        List<Future<Boolean>> results = new ArrayList<>();

        for(int i = 0; i < threadCount; i++){
            results.add(executorService.submit(()->{
                try{
                    latch.countDown();
                    latch.await();
                    // 서비스에서 포인트 충전
                    pointHistoryService.insertChargeUserPointHistory(pointHistoryDTOParam);
                    log.info("충전 성공");

                    return true;
                }catch (ServiceDataNotFoundException e){
                    log.info("ServiceDataNotFoundException error : {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace() );

                    return false;
                }catch (DomainModelParamInvalidException e){
                    log.info("DomainModelParamInvalidException error : {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace() );

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

        long resultUserPoint = USER_INIT_POINT + (successCount * chargePointPerThread);

        User user = userRepository.findUserByUserId(saveUser.getUserId()).orElseThrow();

        log.info("유저 포인트 : {}", user.getPoint() );

        log.info("포인트 충전 내역 결과 포인트: {}", resultUserPoint);

        Assertions.assertThat(user.getPoint()).isEqualTo(resultUserPoint);

        log.info("실행 시간 : {} ms", endTime - startTime);
    }
}
