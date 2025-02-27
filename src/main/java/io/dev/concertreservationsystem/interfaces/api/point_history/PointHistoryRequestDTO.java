package io.dev.concertreservationsystem.interfaces.api.point_history;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.point_history.PointHistoryFacadeDTOParam;
import io.dev.concertreservationsystem.common.validation.annotation.NotInvalidPointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PointHistoryRequestDTO{

    Long pointHistoryId;

    Long paymentId;

    @NotNull
    String userId;

    @NotNull
    PointTransactionType type;

    @NotNull
    Long amount;

    public PointHistoryFacadeDTOParam convertToPointHistoryFacadeDTOParam() {
        return PointHistoryFacadeDTOParam.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .build();
    }
}


