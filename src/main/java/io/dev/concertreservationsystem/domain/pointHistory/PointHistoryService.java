package io.dev.concertreservationsystem.domain.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.factory.SimplePointHistoryFactory;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreatePointHistory;
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
    private final SimplePointHistoryFactory simplePointHistoryFactory;

    @Transactional
    @Validated(CreatePointHistory.class)
    public List<PointHistoryDTOResult> insertUserPointHistory(@Valid PointHistoryDTOParam pointHistoryDTOParam) {

        User user = userRepository.findUserByUserId(pointHistoryDTOParam.userId())
                .orElseThrow(()->{
                    log.debug("user not found");
                    throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                });

        // 포인트 수정
        user.updatePoint(pointHistoryDTOParam.amount());

        // 포인트 수정한 유저 정보 저장(put)
        userRepository.updateUser(user);

        // PointHistory 타입 객체 생성
        PointHistory pointHistory = simplePointHistoryFactory.createPointHistory(pointHistoryDTOParam.userId(), pointHistoryDTOParam.type(), pointHistoryDTOParam.amount(), user.getPoint());

        // 포인트 충전 차감 내역 저장
        pointHistoryRepository.savePointHistory(pointHistory);

        // 유저의 포인트 충전 차감 내역 목록 반환
        return pointHistoryRepository.findPointHistoriesByUserId(pointHistory.getUserId())
                                                .orElseThrow(()->{
                                                    log.debug("point history not found");
                                                    throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                                                }).stream().map(PointHistory::convertToPointHistoryDTOResult).collect(Collectors.toList());


    }
}
