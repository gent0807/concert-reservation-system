package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.common.validation.interfaces.CreatePointHistory;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Validated(CreatePointHistory.class)
    @Transactional
    public List<PointHistoryDTOResult> insertChargeUserPointHistory(@Valid PointHistoryDTOParam pointHistoryDTOParam) {

        // 유저 포인트 동시 충전에 대한 동시성 제어 위해 데이터베에스 테이블 특정 유저 row lock: 각 트랜잭션마다 적용
        User user = userRepository.findUserByUserIdWithLock(pointHistoryDTOParam.userId());

        // 포인트 수정
        user.chargePoint(pointHistoryDTOParam.amount());

        // 포인트 수정한 유저 정보 저장(put)
        userRepository.save(user);

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        PointHistory pointHistory = PointHistory.createPointHistory(pointHistoryDTOParam.userId(), pointHistoryDTOParam.type(), pointHistoryDTOParam.amount(), user.getPoint());

        // 포인트 충전 차감 내역 저장
        pointHistoryRepository.save(pointHistory);

        // 유저의 포인트 충전 차감 내역 목록 반환
        return pointHistoryRepository.findPointHistoriesByUserId(pointHistory.getUserId())
                                                .orElseThrow(()->{
                                                    throw new ServiceDataNotFoundException(ErrorCode.POINT_HISTORY_SAVE_FAILED, "POINT_HISTORY SERVICE", "insertUserPointHistory");
                                                }).stream().map(PointHistory::convertToPointHistoryDTOResult).collect(Collectors.toList());

    }

    @Validated(ProcessPayment.class)
    public void useUserPoint(@Valid PointHistoryDTOParam pointHistoryDTOParam) {

        // 유저 정보가 없으면, exception 발생
        User user = userRepository.findUserByUserIdWithLock(pointHistoryDTOParam.userId());

        // 좌석 예약 정보가 없으면 exception 발생
        reservationRepository.findReservationsByUserIdAndPaymentIdWithLock(pointHistoryDTOParam.userId(), pointHistoryDTOParam.paymentId()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, "POINT_HISTORY SERVICE", "useUserPoint");
        });

        // 결제 정보 아이디로 결제 정보 find
        Payment payment = paymentRepository.findPaymentByPaymentIdWithLock(pointHistoryDTOParam.paymentId()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.PAYMENT_NOT_FOUND, "POINT_HISTORY SERVICE", "useUserPoint");
        });

        // 유저 포인트 잔고와 결제 금액 비교
        userRepository.findUserByUserIdWithLock(pointHistoryDTOParam.userId()).checkPrice(payment.getTotalPrice());

        user.usePoint(payment.getTotalPrice());

        userRepository.save(user);

        pointHistoryRepository.save(PointHistory.createPointHistory(pointHistoryDTOParam.userId(), PointTransactionType.USE, payment.getTotalPrice(), user.getPoint() ));

    }
}
