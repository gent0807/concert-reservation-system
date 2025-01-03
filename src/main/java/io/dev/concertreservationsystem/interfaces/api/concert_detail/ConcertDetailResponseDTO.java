package io.dev.concertreservationsystem.interfaces.api.concert_detail;

import java.time.LocalDateTime;

public record ConcertDetailResponseDTO(
        Long concertDetailId,
        Long concertBasicId,
        Long concertDetailStatusId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
