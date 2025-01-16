package io.dev.concertreservationsystem.interfaces.api.concert_basic;

import io.dev.concertreservationsystem.application.concertBasic.ConcertBasicFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert-basics")
@Tag(name="콘서트 기본 정보 관련 API", description="콘서트 기본 정보 관련 api입니다.")
public class ConcertBasicController {

    private final ConcertBasicFacade concertBasicFacade;

    @GetMapping(value = "/all")
    @Operation(summary = "콘서트 기본 정보 전체 목록 조회", description = "콘서트 전체 목록을 조회합니다.")
    public ResponseEntity<List<ConcertBasicResponseDTO>> findAllConcertBasics() {
        /*
                List<ConcertBasicFacadeDTOResult> concertAdminDTOResultList =  concertAdminFacade.findAllConcertBasics();

                List<ConcertBasicResponseDTO> concertBasicResponseDTOList = ConcertBasicFacadeDTOResult.convertToConcertAdminDTOResultList(concertAdminDTOResultList);

                return ResponseEntity.ok().body(concertBasicResponseDTOList);
        */

        ConcertBasicResponseDTO concertBasicResponseDTO = ConcertBasicResponseDTO.builder().build();

        return ResponseEntity.ok().body(List.of(concertBasicResponseDTO));
    }

    @GetMapping(value = "/condition/{start-date}/{end-date}")
    @Operation(summary = "날짜 조건에 따른 콘서트 기본 정보 목록 조회", description = "날짜 조건에 따른 콘서트 목록을 조회합니다.")
    public ResponseEntity<List<ConcertBasicResponseDTO>> findConcertBasicsByStartDateAndEndDate(@PathVariable("start-date") LocalDateTime startDate,
                                                                                                @PathVariable("end-date") LocalDateTime endDate) {

        /*
                // startDate와 endDate를 이용한 ConcertBasicFacadeDTOParam 타입 객체 생성
                ConcertBasicFacadeDTOParam concertAdminDTOParam = ConcertBasicFacadeDTOParam.builder()
                                                                .startDate(startDate)
                                                                .endDate(endDate)
                                                                .build();

                List<ConcertBasicFacadeDTOResult> concertAdminDTOResultList = concertAdminFacade.findConcertBasicsByStartDateAndEndDate(concertAdminDTOParam);

                List<ConcertBasicResponseDTO> concertBasicResponseDTOList = ConcertBasicFacadeDTOResult.convertToConcertAdminDTOResultList(concertAdminDTOResultList);

                return ResponseEntity.ok().body(List.of(concertBasicResponseDTOList));
        */

        ConcertBasicResponseDTO concertBasicResponseDTO = ConcertBasicResponseDTO.builder().build();

        return ResponseEntity.ok().body(List.of(concertBasicResponseDTO));
    }

}
