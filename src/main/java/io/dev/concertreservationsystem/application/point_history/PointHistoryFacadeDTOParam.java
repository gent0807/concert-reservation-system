package io.dev.concertreservationsystem.application.point_history;

import io.dev.concertreservationsystem.domain.point_history.PointHistoryDTOParam;
import io.dev.concertreservationsystem.interfaces.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PointHistoryFacadeDTOParam(
        Long pointHistoryId,
        Long paymentId,

        @NotBlank
        String userId,

        @NotBlank
        @NotInvalidPointTransactionType
        PointTransactionType type,

        @NotBlank
        @Min(0)
        Long amount
) {

    public PointHistoryDTOParam convertToPointHistoryDTOParam() {
        return PointHistoryDTOParam.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .build();
    }
}
