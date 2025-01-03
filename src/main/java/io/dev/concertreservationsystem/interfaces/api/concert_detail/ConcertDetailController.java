package io.dev.concertreservationsystem.interfaces.api.concert_detail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert/details")
@Tag(name="ConcertDetail API", description="ConcertDetailController")
public class ConcertDetailController {

        @PostMapping("/reservable")
        @Operation(summary = "예약가능한 콘서트 실제 공연 목록 조회", description = "조건에 맞는 예약가능한 콘서트 실제 공연 목록 조회")
        public ResponseEntity<List<ConcertDetailResponseDTO>> getReservableConcerts(@RequestBody ConcertDetailRequestDTO concertDetailRequestDTO,
                                                                                    @RequestHeader("Authorization") String tokenId) {

            List<ConcertDetailResponseDTO> concertDetailResponseDTOS = null;

            concertDetailResponseDTOS.add(new ConcertDetailResponseDTO(concertDetailRequestDTO.concertDetailId(), concertDetailRequestDTO.concertDetailStatusId(),
                                                            concertDetailRequestDTO.concertBasicId(), concertDetailRequestDTO.startDate(), concertDetailRequestDTO.endDate(),
                                                            concertDetailRequestDTO.createdAt(), concertDetailRequestDTO.updatedAt()));

        return ResponseEntity.ok().header("Authorization", tokenId).body(concertDetailResponseDTOS);
    }
}
