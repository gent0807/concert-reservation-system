package io.dev.concertreservationsystem.domain.pointHistory;

import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.pointHistory.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
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
        Integer amount,

        Long paymentId
) {
}
