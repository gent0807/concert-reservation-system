package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.application.point_history.PointHistoryFacadeDTOResult;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryDTOResult(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Long amount,
        Long resultPoint,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {

    public PointHistoryFacadeDTOResult convertToPointHistoryFacadeDTOResult() {
        return PointHistoryFacadeDTOResult.builder()
                .pointHistoryId(this.pointHistoryId)
                .paymentId(this.paymentId)
                .userId(this.userId)
                .type(this.type)
                .amount(this.amount)
                .resultPoint(this.resultPoint)
                .created_at(this.created_at)
                .updated_at(this.updated_at)
                .build();
    }
}
