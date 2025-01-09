package io.dev.concertreservationsystem.domain.pointHistory;

import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
    String userId,
    PointTransactionType type,
    Long amount
) {
}
