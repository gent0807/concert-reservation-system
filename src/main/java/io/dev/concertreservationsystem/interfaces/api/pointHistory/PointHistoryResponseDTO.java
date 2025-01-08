package io.dev.concertreservationsystem.interfaces.api.pointHistory;

import java.time.LocalDateTime;

public record PointHistoryResponseDTO(
        Long pointHistoryId,
        Long paymentId,
        Long userId,
        PointTransactionType type,
        Long amount,
        Long resultPoint,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
