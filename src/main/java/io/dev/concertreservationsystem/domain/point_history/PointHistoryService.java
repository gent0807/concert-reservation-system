package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PointHistoryNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreatePointHistory;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    @Validated(CreatePointHistory.class)
    public List<PointHistoryDTOResult> insertUserPointHistory(@Valid PointHistoryDTOParam pointHistoryDTOParam) {

        User user = userRepository.findUserByUserId(pointHistoryDTOParam.userId())
                .orElseThrow(()->{
                    log.debug("user not found");
                    throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                });

        // 포인트 수정
        user.chargePoint(pointHistoryDTOParam.amount());

        // 포인트 수정한 유저 정보 저장(put)
        userRepository.saveUser(user);

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        PointHistory pointHistory = PointHistory.createPointHistory(pointHistoryDTOParam.userId(), pointHistoryDTOParam.type(), pointHistoryDTOParam.amount(), user.getPoint());

        // 포인트 충전 차감 내역 저장
        pointHistoryRepository.savePointHistory(pointHistory);

        // 유저의 포인트 충전 차감 내역 목록 반환
        return pointHistoryRepository.findPointHistoriesByUserId(pointHistory.getUserId())
                                                .orElseThrow(()->{
                                                    log.debug("point history not found");
                                                    throw new PointHistoryNotFoundException(ErrorCode.POINT_HISTORY_SAVE_FAILED);
                                                }).stream().map(PointHistory::convertToPointHistoryDTOResult).collect(Collectors.toList());


    }

    public void useUserPoint(PointHistoryDTOParam pointHistoryDTOParam) {
        User user = userRepository.findUserByUserId(pointHistoryDTOParam.userId()).orElseThrow(
                ()->{
                    log.debug("user not found");
                    throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                }
        );

        Payment payment = paymentRepository.findPaymentByPaymentId(pointHistoryDTOParam.paymentId());

        user.usePoint(payment.getTotalPrice());

        userRepository.saveUser(user);

        pointHistoryRepository.savePointHistory(PointHistory.createPointHistory(pointHistoryDTOParam.userId(), PointTransactionType.USE, payment.getTotalPrice(), user.getPoint() ));

    }
}
