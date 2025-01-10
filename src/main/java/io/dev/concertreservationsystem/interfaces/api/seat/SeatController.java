package io.dev.concertreservationsystem.interfaces.api.seat;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
@Tag(name="예약 가능 좌석 API(과제)", description="예약 가능한 좌석을 조회하는 api입니다.")
public class SeatController {

    private final ConcertReserveAdminFacade concertReserveAdminFacade;

    @GetMapping("{concert-detail-id}/reservable")
    @Operation(summary = "예약 가능한 콘서트 실제 공연 좌석 목록 조회", description = "예약 가능한 콘서트 실제 공연 좌석 목록 조회")
    public ResponseEntity<List<SeatResponseDTO>> findReservableSeats(@PathVariable("concert-detail-id") Long concertDetailId) {

        // concertReserveAdminDTOParam의 concertDetailId를 이용하여 해당 콘서트 실제 공연의 예약 가능한 좌석 목록을 조회하는,
        // 현재 참조된 concertReserveAdminFacade 타입 객체의 findReservableSeats 메소드 호출
        List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList = concertReserveAdminFacade.findReservableSeats(ConcertReserveAdminDTOParam.builder()
                                                                                                                                        .concertDetailId(concertDetailId)
                                                                                                                                        .build());


        return ResponseEntity.ok().body(ConcertReserveAdminDTOResult.convertToSeatResponseDTOList(concertReserveAdminDTOResultList));



    }
}
