package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class PointHistoryConcurrencyTest {
    @Autowired
    private PointHistoryService pointHistoryService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_USER_ID = UUID.randomUUID().toString(); // 테스트용 유저 ID

    @BeforeEach
    void setUp(){

        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName("tester")
                .gender(UserGenderType.MALE)
                .age(31)
                .point(1000L)
                .build();

        userRepository.saveUser(user);

    }

    @Test
    @Transactional
    public void 동일한_userId로_여러_개의_포인트_충전_요청이_동시에_들어오는_경우_정확한_값으로_계산되어야_한다() throws InterruptedException {

    }
}
