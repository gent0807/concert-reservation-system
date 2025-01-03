package io.dev.concertreservationsystem.interfaces.api.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name="Reservation API", description="ReservationController")
public class ReservationController {

    @PostMapping("/new")
    @Operation(summary = "콘서트 실제 공연 좌석 임시 예약 등록", description = "콘서트 실제 공연의 예약 가능한 좌석에 대한 임시 예약을 등록합니다.")
    public ResponseEntity<ReservationResponseDTO> insertReservation(@RequestBody ReservationRequestDTO reservationRequestDTO,
                                                                    @RequestHeader("Authorization") String tokenId) {

        ReservationResponseDTO reservationResponse = new ReservationResponseDTO(reservationRequestDTO.reservationId(), reservationRequestDTO.seatId(), reservationRequestDTO.reservationStatusId(),
                                                                            reservationRequestDTO.createdAt(), reservationRequestDTO.updatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).header("Authorization", tokenId).body(reservationResponse);
    }
}
