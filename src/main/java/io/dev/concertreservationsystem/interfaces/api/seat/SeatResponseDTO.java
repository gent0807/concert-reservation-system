package io.dev.concertreservationsystem.interfaces.api.seat;

import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SeatResponseDTO(
        Long seatId,
        Long concertDetailId,
        Integer seatNumber,
        SeatStatusType seatStatus,
        Long price,
        LocalDateTime expiredAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
