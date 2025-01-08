package io.dev.concertreservationsystem.interfaces.api.concert_detail;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert-details")
@Tag(name="예약 가능 날짜 API(과제)", description="예약 가능한 실제 공연 목록을 조회하는 api입니다.")
public class ConcertDetailController {

        private final ConcertReserveAdminFacade concertReserveAdminFacade;

        @GetMapping("{concert-id}/reservable")
        @Operation(summary = "예약가능한 콘서트 실제 공연 목록 조회", description = "예약가능한 콘서트 실제 공연 목록 조회")
        public ResponseEntity<List<ConcertDetailResponseDTO>> findReservableConcertDetails(@PathVariable("concert-id") Long concertId) {
           /*
                // concertId를 이용하여 concertReservationDTOParam 생성
                ConcertReserveAdminDTOParam concertReserveAdminDTOParam = ConcertReserveAdminDTOParam.builder()
                                                                                            .concertId(concertId);

                // concertReserveAdminDTOParam의 concertId를 이용하여 해당 콘서트의 예약 가능한 날짜, 예약 가능한 실제 공연 목록을 조회하는,
                // 현재 참조된 ConcertReserveAdminFacade 타입 객체의 findReservableConcertDetails 메소드를 호출한다.
                List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList = concertReserveAdminFacade.findReservableConcertDetails(concertReserveAdminDTOParam);

                List<ConcertDetailResponseDTO> concertDetailResponseDTOList = ConcertReserveAdminDTOResult.convertToConcertDetailResponseDTOList(concertReserveAdminDTOResultList);

                return ResponseEntity.status(HttpStatus.OK).body(concertDetailResponseDTOList);
            */
            List<ConcertDetailResponseDTO> concertDetailResponseDTOs = null;

            return ResponseEntity.ok().body(concertDetailResponseDTOs);
        }
}
