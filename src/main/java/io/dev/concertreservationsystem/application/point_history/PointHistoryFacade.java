package io.dev.concertreservationsystem.application.point_history;

import io.dev.concertreservationsystem.domain.point_history.PointHistoryDTOResult;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryService;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreatePointHistory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Validated
public class PointHistoryFacade {

    private final PointHistoryService pointHistoryService;

    // 1. 유저 포인트 충전 차감 내역 추가 저장, Post
    @Validated(CreatePointHistory.class)
    public List<PointHistoryFacadeDTOResult> insertUserPointHistory(@Valid PointHistoryFacadeDTOParam pointHistoryFacadeDTOParam) {

            // pointHistoryDTOParam에 담긴 정보를 바탕으로 유저의 포인트를 수정하고, 유저의 포인트 충전/차감 내역을 추가 저장하는,
            // 현재 참조된 PointHistoryService 타입 객체의 insertUserPointHistory 메소드 호출
            List<PointHistoryDTOResult> pointHistoryDTOResultList = pointHistoryService.insertUserPointHistory(pointHistoryFacadeDTOParam.convertToPointHistoryDTOParam());

            return pointHistoryDTOResultList.stream().map(PointHistoryDTOResult::convertToPointHistoryFacadeDTOResult).collect(Collectors.toList());


    }

    // 2. 유저의 포인트 충전/차감 내역 전체 목록 조회
    public void findUserPointAllHistories(PointHistoryFacadeDTOParam pointHistoryFacadeDTOParam) {
        /*
            // pointHistoryDTOParam의 userId와 일치하는 유저의 포인트 충전/차감 내역 전체 목록을 조회하는,
            // 현재 참조된 PointHistoryService 타입 객체의 findUserPointAllHistories 메소드 호출
            List<PointHistoryDTOResult> pointHistoryDTOResultList = pointHistoryService.findUserPointAllHistories(pointHistoryFacadeDTOParam.convertToPointHistoryDTOParam());

            return PointHistoryDTOResult.convertToPointHistoryAdminDTOResultList();
        */
    }

    // 3. 기간 조건에 다른유저의 포인트 충전/차감 내역 목록 조회
    public void findPointHistoriesByUserIdAndStartDateAndEndDate(PointHistoryFacadeDTOParam pointHistoryFacadeDTOParam){
      /*
        // pointHistoryDTOParam의 userId와 일치하는 유저의 포인트 충전/차감 내역 목록 중 기간 조건을 만족하는 포인트 충전/차감 내역 목록을 조회하는
        // 현재 참조된 PointHistoryService 타입 객체의 findPointHistoriesByUserIdAndStartDateAndEndDate 메소드 호출
        List<PointHistoryDTOResult> pointHistoryDTOResultList = pointHistoryService.findPointHistoriesByUserIdAndStartDateAndEndDateAllHistories(pointHistoryFacadeDTOParam.convertToPointHistoryDTOParam());

        return PointHistoryDTOResult.convertToPointHistoryAdminDTOResultList();
    */
    }
}