package io.dev.concertreservationsystem.interfaces.api.pointHistory;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryResponseDTO(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Integer amount,
        Long resultPoint,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
