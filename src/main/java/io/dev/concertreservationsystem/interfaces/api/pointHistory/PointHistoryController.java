package io.dev.concertreservationsystem.interfaces.api.pointHistory;

import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminDTOParam;
import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminDTOResult;
import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminFacade;
import io.dev.concertreservationsystem.application.user.UserAdminDTOParam;
import io.dev.concertreservationsystem.application.user.UserAdminDTOResult;
import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryDTOParam;
import io.dev.concertreservationsystem.interfaces.api.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pointHistories")
@Tag(name="유저 포인트 충전/조회 API(과제)", description="유저의 포인트를 충전하고 조회하는 api입니다.")
public class PointHistoryController {

    private final PointHistoryAdminFacade pointHistoryAdminFacade;

    @PostMapping("/new")
    @Operation(summary = "유저 포인트 충전/차감 내역 추가 저장", description = "유저의 포인트 충전/차감 내역을 추가 저장하고 유저 포인트 정보를 수정합니다.")
    public ResponseEntity<List<PointHistoryResponseDTO>> insertUserPointHistory(@RequestBody PointHistoryRequestDTO pointHistoryRequestDTO) {
            // 요청에 담긴 PointHistoryRequestDTO 타입 데이터의 userId와 일치하는 회원의 포인트를 수정하고, 포인트 충전/차감 내역을 추가 저장하는,
            // 현재 참조된 PointHistoryAdminFacade 타입 객체 pointHistoryAdminFacade의 insertUserPointHistory 메소드 호출
            List<PointHistoryAdminDTOResult> pointHistoryAdminDTOResultList = pointHistoryAdminFacade.insertUserPointHistory(pointHistoryRequestDTO.convertToPointHistoryAdminDTOParam());

            return ResponseEntity.status(HttpStatus.CREATED).body(pointHistoryAdminDTOResultList.stream()
                                                                    .map(PointHistoryAdminDTOResult::convertToPointHistoryResponseDTO).collect(Collectors.toList()));

    }

    @GetMapping(value = "{user-id}/all")
    @Operation(summary = "유저의 포인트 충전/차감 내역 전체 목록 조회", description = "유저의 포인트 충전/차감 내역을 전체 조회합니다.")
    public ResponseEntity<List<PointHistoryResponseDTO>> findUserPointAllHistoriesByUserId(@PathVariable("user-id") Long userId) {
        /*
            // userId를 이용한 PointHistoryAdminDTOParam 타입 객체 생성
            PointHistoryAdminDTOParam pointHistoryAdminDTOParam = PointHistoryAdminDTOParam.builder()
                                                                        .userId(userId)
                                                                        .build();

            // pointHistoryAdminDTOParam의 userId와 일치하는 유저 존재 여부를 확인하고 해당 유저의 포인트 충전/차감 내역 전체 목록을  조회하는,
            // 현재 참조된 PointHistoryAdminFacade 타입 객체 pointHistoryAdminFacade의 findUserPointAllHistories 메소드 호출
            List<PointHistoryAdminDTOResult> pointHistoryAdminDTOResultList = pointHistoryAdminFacade.findUserPointAllHistories(pointHistoryAdminDTOParam);

            // List<PointHistoryAdminDTOResult> 타입의 객체를 List<PointHistoryResponseDTO> 타입의 객체로 변환하는 static 메소드 호출
            List<PointHistoryResponseDTO> pointHistoryResponseDTOList = PointHistoryAdminDTOResult.convertToPointHistoryResponseDTOList(pointHistoryAdminDTOResultList);

            return ResponseEntity.status(HttpStatus.OK).body(pointHistoryResponseDTOList);
        */
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "{user-id}/condition/{start-data}/{end-date}")
    @Operation(summary = "날짜 조건에 따른 유저의 포인트 충전/차감 내역 목록 조회", description = "유저의 포인트 충전/차감 내역을 날짜 조건에 따라 조회합니다.")
    public ResponseEntity<List<PointHistoryResponseDTO>> findPointHistoriesByUserIdAndStartDateAndEndDate(@PathVariable("user-id") Long userId,
                                                                            @PathVariable("start-date") LocalDateTime startDate,
                                                                            @PathVariable("end-date") LocalDateTime endDate) {

        /*

            // userId, startDate, endDate 이용한 PointHistoryAdminDTOParam 타입 객체 생성
            PointHistoryAdminDTOParam pointHistoryAdminDTOParam = PointHistoryAdminDTOParam.builder()
                                                                       .userId(userId)
                                                                       .startDate(startDate)
                                                                       .endDate(endDate)
                                                                       .build();

            // 요청에 담긴 userId와 일치하고 기간 조건을 만족시키는 유저의 포인트 충전/차감 내역 목록 조회하는,
            // 현재 참조된 PointHistoryAdminFacade 타입 객체의 findPointHistoriesByUserIdAndStartDateAndEndDate 메소드 호출
            List<PointHistoryAdminDTOResult> pointHistoryAdminDTOResultList = pointHistoryAdminFacade.findPointHistoriesByUserIdAndStartDateAndEndDate(pointHistoryAdminDTOParam);

            // List<PointHistoryAdminDTOResult> 타입의 객체를 List<PointHistoryResponseDTO> 타입의 객체로 변환하는 static 메소드 호출
                List<PointHistoryResponseDTO> pointHistoryResponseDTOList = PointHistoryAdminDTOResult.convertToPointHistoryResponseDTOList(pointHistoryAdminDTOResultList);


            return ResponseEntity.status(HttpStatus.OK).body(pointHistoryResponseDTOList);

         */

        return ResponseEntity.ok().build();
    }

}
