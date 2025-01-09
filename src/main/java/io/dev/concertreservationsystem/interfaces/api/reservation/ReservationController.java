package io.dev.concertreservationsystem.interfaces.api.reservation;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name="좌석 예약 API(과제)", description="좌석을 예약하는 api입니다.")
public class ReservationController {

    private final ConcertReserveAdminFacade concertReserveAdminFacade;

    @PostMapping
    @Operation(summary = "콘서트 실제 공연 좌석들에 대한 임시 예약 등록", description = "콘서트 실제 공연의 예약 가능한 좌석에 대한 임시 예약들을 등록합니다.")
    public ResponseEntity<List<ReservationResponseDTO>> insertReservations(@RequestBody List<ReservationRequestDTO> reservationRequestDTOList) {

            // concertReserveAdminDTOParamList 정보를 이용하여 콘서트 실제 공연의 좌석들에 대한 예약 정보를 등록하고,
            // 콘서트 실제 공연 좌석들의 예약 상태를 확인/수정하고, 콘서트 실제 공연 좌석들의 예약 상태를 occupied 상태로 수정하는,
            // 현재 참조된  ConcertReserveAdminFacade 타입 객체의 insertReservations 메소드 호출
            List<ConcertReserveAdminDTOResult> concertReserveAdminDTOResultList = concertReserveAdminFacade.insertReservations(ReservationRequestDTO.convertToConcertReserveAdminDTOParamList(reservationRequestDTOList));

            // concertReserveAdminDTOResultList를  reservationResponseDTOList로 변환
            List<ReservationResponseDTO> reservationResponseDTOList = ConcertReserveAdminDTOResult.convertToReservationResponseDTOList(concertReserveAdminDTOResultList);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponseDTOList);


    }
}
