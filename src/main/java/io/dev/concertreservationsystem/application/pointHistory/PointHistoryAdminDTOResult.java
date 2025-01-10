package io.dev.concertreservationsystem.application.pointHistory;

import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointHistoryResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryAdminDTOResult(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Integer amount,
        Long resultPoint,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
    public PointHistoryResponseDTO convertToPointHistoryResponseDTO() {
        return PointHistoryResponseDTO.builder()
                .pointHistoryId(pointHistoryId)
                .paymentId(paymentId)
                .userId(userId)
                .type(type)
                .amount(amount)
                .resultPoint(resultPoint)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
    }
}
