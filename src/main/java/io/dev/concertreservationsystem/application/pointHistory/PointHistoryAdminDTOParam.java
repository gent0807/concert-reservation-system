package io.dev.concertreservationsystem.application.pointHistory;

import io.dev.concertreservationsystem.domain.pointHistory.PointHistoryDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PointHistoryAdminDTOParam(
        Long pointHistoryId,
        Long paymentId,

        @NotNull
        @NotBlank
        String userId,

        @NotNull
        @NotBlank
        @NotInvalidPointTransactionType
        PointTransactionType type,

        @NotNull
        @NotBlank
        @Min(0)
        Integer amount
) {

    public PointHistoryDTOParam convertToPointHistoryDTOParam() {
        return PointHistoryDTOParam.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .build();
    }
}
