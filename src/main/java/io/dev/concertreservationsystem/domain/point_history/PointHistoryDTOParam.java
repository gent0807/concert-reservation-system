package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.common.validation.interfaces.CreatePointHistory;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
        @NotNull(groups = {ProcessPayment.class, CreatePointHistory.class})
        String userId,

        @NotNull(groups = CreatePointHistory.class)
        @NotInvalidPointTransactionType(groups = CreatePointHistory.class)
        PointTransactionType type,

        @NotNull(groups = CreatePointHistory.class)
        @Min(value = 0, groups = CreatePointHistory.class)
        Long amount,

        @NotNull(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        Long paymentId
) {
}
