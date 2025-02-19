package io.dev.concertreservationsystem.interfaces.api.point_history;

import io.dev.concertreservationsystem.application.point_history.PointHistoryFacadeDTOResult;
import io.dev.concertreservationsystem.application.point_history.PointHistoryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    private final PointHistoryFacade pointHistoryFacade;

    @PostMapping("/new")
    @Operation(summary = "유저 포인트 충전 내역 저장", description = "유저의 포인트 충전 내역을 저장하고 유저 포인트 정보를 수정합니다.")
    public ResponseEntity<List<PointHistoryResponseDTO>> insertChargeUserPointHistory(@Valid @RequestBody PointHistoryRequestDTO pointHistoryRequestDTO) {
            // 요청에 담긴 PointHistoryRequestDTO 타입 데이터의 userId와 일치하는 회원의 포인트를 수정하고, 포인트 충전 내역을 저장하는,
            // 현재 참조된 PointHistoryFacade 타입 객체 pointHistoryAdminFacade의 insertChargeUserPointHistory 메소드 호출
            List<PointHistoryFacadeDTOResult> pointHistoryFacadeDTOResultList = pointHistoryFacade.insertChargeUserPointHistory(pointHistoryRequestDTO.convertToPointHistoryFacadeDTOParam());

            return ResponseEntity.status(HttpStatus.CREATED).body(pointHistoryFacadeDTOResultList.stream()
                                                                    .map(PointHistoryFacadeDTOResult::convertToPointHistoryResponseDTO).collect(Collectors.toList()));

    }

    @GetMapping(value = "{user-id}/all")
    @Operation(summary = "유저의 포인트 충전/차감 내역 전체 목록 조회", description = "유저의 포인트 충전/차감 내역을 전체 조회합니다.")
    public ResponseEntity<List<PointHistoryResponseDTO>> findUserPointAllHistoriesByUserId(@PathVariable("user-id") Long userId) {
        /*
            // userId를 이용한 PointHistoryFacadeDTOParam 타입 객체 생성
            PointHistoryFacadeDTOParam pointHistoryAdminDTOParam = PointHistoryFacadeDTOParam.builder()
                                                                        .userId(userId)
                                                                        .build();

            // pointHistoryAdminDTOParam의 userId와 일치하는 유저 존재 여부를 확인하고 해당 유저의 포인트 충전/차감 내역 전체 목록을  조회하는,
            // 현재 참조된 PointHistoryFacade 타입 객체 pointHistoryAdminFacade의 findUserPointAllHistories 메소드 호출
            List<PointHistoryFacadeDTOResult> pointHistoryAdminDTOResultList = pointHistoryAdminFacade.findUserPointAllHistories(pointHistoryAdminDTOParam);

            // List<PointHistoryFacadeDTOResult> 타입의 객체를 List<PointHistoryResponseDTO> 타입의 객체로 변환하는 static 메소드 호출
            List<PointHistoryResponseDTO> pointHistoryResponseDTOList = PointHistoryFacadeDTOResult.convertToPointHistoryResponseDTOList(pointHistoryAdminDTOResultList);

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

            // userId, startDate, endDate 이용한 PointHistoryFacadeDTOParam 타입 객체 생성
            PointHistoryFacadeDTOParam pointHistoryAdminDTOParam = PointHistoryFacadeDTOParam.builder()
                                                                       .userId(userId)
                                                                       .startDate(startDate)
                                                                       .endDate(endDate)
                                                                       .build();

            // 요청에 담긴 userId와 일치하고 기간 조건을 만족시키는 유저의 포인트 충전/차감 내역 목록 조회하는,
            // 현재 참조된 PointHistoryFacade 타입 객체의 findPointHistoriesByUserIdAndStartDateAndEndDate 메소드 호출
            List<PointHistoryFacadeDTOResult> pointHistoryAdminDTOResultList = pointHistoryAdminFacade.findPointHistoriesByUserIdAndStartDateAndEndDate(pointHistoryAdminDTOParam);

            // List<PointHistoryFacadeDTOResult> 타입의 객체를 List<PointHistoryResponseDTO> 타입의 객체로 변환하는 static 메소드 호출
                List<PointHistoryResponseDTO> pointHistoryResponseDTOList = PointHistoryFacadeDTOResult.convertToPointHistoryResponseDTOList(pointHistoryAdminDTOResultList);


            return ResponseEntity.status(HttpStatus.OK).body(pointHistoryResponseDTOList);

         */

        return ResponseEntity.ok().build();
    }

}
