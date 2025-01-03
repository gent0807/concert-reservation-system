package io.dev.concertreservationsystem.interfaces.api.concert_basic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert/basics")
@Tag(name="ConcertBasic API", description="ConcertBasicController")
public class ConcertBasicController
{
    @PostMapping("/current")
    @Operation(summary = "콘서트 목록 조회", description = "현재 콘서트 목록을 조회합니다.")
    public ResponseEntity<List<ConcertBasicResponseDTO>> getCurrentConcerts(@RequestBody ConcertBasicRequestDTO concertBasicRequestDTO)  {

        List<ConcertBasicResponseDTO> concertBasicResponseDTOS = null;

        concertBasicResponseDTOS.add(new ConcertBasicResponseDTO(concertBasicRequestDTO.concertBasicId(),
                concertBasicRequestDTO.concertName(), concertBasicRequestDTO.genreId(), concertBasicRequestDTO.createdAt(), concertBasicRequestDTO.updatedAt()));

        return ResponseEntity.ok(concertBasicResponseDTOS);
    }
}
