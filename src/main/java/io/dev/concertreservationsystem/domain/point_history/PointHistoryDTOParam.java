package io.dev.concertreservationsystem.domain.point_history;

import io.dev.concertreservationsystem.interfaces.common.validation.annotation.NotInvalidPointTransactionType;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreatePointHistory;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.api.point_history.PointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PointHistoryDTOParam(
        @NotBlank(groups = {ProcessPayment.class, CreatePointHistory.class})
        String userId,

        @NotBlank(groups = CreatePointHistory.class)
        @NotInvalidPointTransactionType(groups = CreatePointHistory.class)
        PointTransactionType type,

        @NotBlank(groups = CreatePointHistory.class)
        @Min(value = 0, groups = CreatePointHistory.class)
        Integer amount,

        @NotBlank(groups = ProcessPayment.class)
        @Min(value = 0, groups = ProcessPayment.class)
        Long paymentId
) {
}
