package io.dev.concertreservationsystem.application.point_history;

import io.dev.concertreservationsystem.interfaces.api.point_history.PointHistoryResponseDTO;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryFacadeDTOResult(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Long amount,
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
