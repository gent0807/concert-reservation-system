package io.dev.concertreservationsystem.application.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryDTOParam;
import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryDTOResult;
import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointHistoryAdminFacade {

    // private final PointHistoryService pointHistoryService;

    // 1. 유저 포인트 충전 차감 내역 추가 저장, Post
    public void insertUserPointHistory(PointHistoryAdminDTOParam pointHistoryAdminDTOParam) {
        /*
            // PointHistoryAdminDTOParam 타입 객체를 PointHistoryDTOParam 타입 객체로 변환
            PointHistoryDTOParam pointHistoryDTOParam = pointHistoryAdminDTOParam.convertToPointHistoryDTOParam();

            // pointHistoryDTOParam에 담긴 정보를 바탕으로 유저의 포인트를 수정하고, 유저의 포인트 충전/차감 내역을 추가 저장하는,
            // 현재 참조된 PointHistoryService 타입 객체의 insertUserPointHistory 메소드 호출
            PointHistoryDTOResult pointHistoryDTOResult = pointHistoryService.insertUserPointHistory(pointHistoryDTOParam);

            // pointHistoryDTOResult 타입 객체를 PointHistoryAdminDTOResult 객체로 변환
            PointHistoryAdminDTOResult pointHistoryAdminDTOResult = pointHistoryDTOResult.convertToPointHistoryDTOResult();

            return pointHistoryAdminDTOResult;

          */
    }

    // 2. 유저의 포인트 충전/차감 내역 전체 목록 조회
    public void findUserPointAllHistories(PointHistoryAdminDTOParam pointHistoryAdminDTOParam) {
        /*
            // PointHistoryAdminDTOParam 타입 객체를 PointHistoryDTOParam 타입 객체로 변환
            PointHistoryDTOParam pointHistoryDTOParam = pointHistoryAdminDTOParam.convertToPointHistoryDTOParam();

            // pointHistoryDTOParam의 userId와 일치하는 유저의 포인트 충전/차감 내역 전체 목록을 조회하는,
            // 현재 참조된 PointHistoryService 타입 객체의 findUserPointAllHistories 메소드 호출
            List<PointHistoryDTOResult> pointHistoryDTOResultList = pointHistoryService.findUserPointAllHistories(pointHistoryDTOParam);

            // List<PointHistoryDTOResult> 타입 객체를 List<PointHistoryAdminDTOResult> 타입 객체로 변환
            List<PointHistoryAdminDTOResult> pointHistoryAdminDTOResultList = PointHistoryDTOResult.convertToPointHistoryAdminDTOResultList();

            return PointHistoryAdminDTOResult;
        */
    }

    // 3. 기간 조건에 다른유저의 포인트 충전/차감 내역 목록 조회
    public void findPointHistoriesByUserIdAndStartDateAndEndDate(PointHistoryAdminDTOParam pointHistoryAdminDTOParam){
      /*
        // PointHistoryAdminDTOParam 타입 객체를 PointHistoryDTOParam 타입 객체로 변환
        PointHistoryDTOParam pointHistoryDTOParam = pointHistoryAdminDTOParam.convertToPointHistoryDTOParam();

        // pointHistoryDTOParam의 userId와 일치하는 유저의 포인트 충전/차감 내역 목록 중 기간 조건을 만족하는 포인트 충전/차감 내역 목록을 조회하는
        // 현재 참조된 PointHistoryService 타입 객체의 findPointHistoriesByUserIdAndStartDateAndEndDate 메소드 호출
        List<PointHistoryDTOResult> pointHistoryDTOResultList = pointHistoryService.findUserPointfindPointHistoriesByUserIdAndStartDateAndEndDateAllHistories(pointHistoryDTOParam);

        // List<PointHistoryDTOResult> 타입 객체를 List<PointHistoryAdminDTOResult> 타입 객체로 변환
        List<PointHistoryAdminDTOResult> pointHistoryAdminDTOResultList = PointHistoryDTOResult.convertToPointHistoryAdminDTOResultList();

        return PointHistoryAdminDTOResult;
    */
    }
}