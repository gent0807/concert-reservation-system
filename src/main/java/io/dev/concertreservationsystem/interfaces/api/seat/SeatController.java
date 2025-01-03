package io.dev.concertreservationsystem.interfaces.api.seat;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
@Tag(name="Seat API", description="SeatController")
public class SeatController {

    @GetMapping("/reservable")
    @Operation(summary = "예약 가능한 콘서트 실제 공연 좌석 목록 조회", description = "조건에 맞는 예약 가능한 콘서트 실제 공연 좌석 목록 조회")
    public ResponseEntity<List<SeatResponseDTO>> getReservableSeats(@RequestBody SeatRequestDTO seatRequestDTO,
                                                                    @RequestHeader("Authorization") String tokenId) {

        List<SeatResponseDTO> seatResponse = null;

        seatResponse.add(new SeatResponseDTO(seatRequestDTO.seatId(), seatRequestDTO.concertDetailId(), seatRequestDTO.seatNumber(),
                                            seatRequestDTO.seatStatusId(), seatRequestDTO.price(), seatRequestDTO.createdAt(), seatRequestDTO.updatedAt()));

        return ResponseEntity.ok().header("Authorization", tokenId).body(seatResponse);
    }
}
