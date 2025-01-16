package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.interfaces.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
        @NotBlank(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        String userId,

        @NotBlank
        @NotInvalidPointTransactionType
        PointTransactionType type,

        @NotBlank
        @Min(0)
        Integer amount,

        @NotBlank(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        Long paymentId
) {
}
