package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
        @NotBlank
        String userId,

        @NotBlank
        @NotInvalidPointTransactionType
        PointTransactionType type,

        @NotBlank
        @Min(0)
        Integer amount,

        Long paymentId
) {
}
