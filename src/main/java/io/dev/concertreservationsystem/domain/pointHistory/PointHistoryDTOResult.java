package io.dev.concertreservationsystem.domain.pointHistory;

import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminDTOParam;
import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminDTOResult;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryDTOResult(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Integer amount,
        Long resultPoint,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {

    public PointHistoryAdminDTOResult convertToPointHistoryDTOResult() {
        return PointHistoryAdminDTOResult.builder()
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
