package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest
public class PointHistoryConcurrencyTest {
    @Autowired
    private PointHistoryService pointHistoryService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_USER_ID = UUID.randomUUID().toString(); // 테스트용 유저 ID

    private static final long USER_INIT_POINT = 10000L;

    @BeforeEach
    void setUp(){

        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName("tester")
                .gender(UserGenderType.MALE)
                .age(31)
                .point(USER_INIT_POINT)
                .build();

        userRepository.saveUser(user);

    }

    @Test
    @Transactional
    public void 동일한_userId로_여러_개의_포인트_충전_요청이_동시에_들어오는_경우_정확한_값으로_계산되어야_한다() throws InterruptedException {

        // 쓰레드 설정
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // 각 쓰레드에서 충전할 포인트
        int chargePointPerThread = 100;

        // 포인트 충전 DTO 생성
        PointHistoryDTOParam pointHistoryDTOParam = PointHistoryDTOParam.builder()
                .userId(TEST_USER_ID)
                .type(PointTransactionType.CHARGE)
                .amount(chargePointPerThread)
                .build();

        // 콜백 함수 정의 리스트
        List<Callable<Void>> tasks = new ArrayList<>();

        // 포인트 충전 성공 개수 계산
        List<Boolean> successList = new ArrayList<>();



        for(int i = 0; i < threadCount; i++){
            tasks.add(()->{

                // 서비스에서 포인트 충전
                pointHistoryService.insertChargeUserPointHistory(pointHistoryDTOParam);

                successList.add(true);


                return null;
            });
        }

        // 여러 쓰레드에서 동시에 insertChargeUserPointHistory 실행
        List<Future<Void>> futureCallBackFunctionList =  executorService.invokeAll(tasks);

        futureCallBackFunctionList.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                successList.add(false);
            }
        });

        // ExecutorService 종료
        executorService.shutdown();

        int chargePointSuccessCount = successList.stream().filter(b->b.equals(Boolean.TRUE)).toList().size();
        long resultUserPoint = USER_INIT_POINT + ((long) chargePointSuccessCount * chargePointPerThread);

        User user = userRepository.findUserByUserIdWithLock(TEST_USER_ID).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.USER_NOT_FOUND, "POINT_HISTORY CONCURRENCY TEST", "유저 포인트 동시 충전 테스트");
        });

        Assertions.assertThat(user.getPoint()).isEqualTo(resultUserPoint);

    }
}
