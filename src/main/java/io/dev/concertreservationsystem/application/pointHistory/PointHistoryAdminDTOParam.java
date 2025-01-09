package io.dev.concertreservationsystem.application.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryDTOParam;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import lombok.Builder;

@Builder
public record PointHistoryAdminDTOParam(
        Long pointHistoryId,
        Long paymentId,
        String userId,
        PointTransactionType type,
        Long amount
) {

    public PointHistoryDTOParam convertToPointHistoryDTOParam() {
        return PointHistoryDTOParam.builder()
                .build();
    }
}
