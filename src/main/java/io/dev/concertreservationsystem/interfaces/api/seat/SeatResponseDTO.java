package io.dev.concertreservationsystem.interfaces.api.seat;

import java.time.LocalDateTime;

public record SeatResponseDTO(
        Long seatId,
        Long concertDetailId,
        Long seatNumber,
        Integer seatStatusId,
        Integer price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
