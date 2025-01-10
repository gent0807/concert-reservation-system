package io.dev.concertreservationsystem.interfaces.api.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ConcertDetailResponseDTO(
        Long concertDetailId,

        Long concertBasicId,

        ConcertDetailStatusType concertDetailStatus,

        LocalDateTime startTime,

        LocalDateTime endTime,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
