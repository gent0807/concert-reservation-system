package io.dev.concertreservationsystem.interfaces.api.point_history;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.point_history.PointHistoryFacadeDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidPointTransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PointHistoryRequestDTO(

        @JsonProperty(value = "pointHistoryId", access = JsonProperty.Access.READ_ONLY)
        Long pointHistoryId,

        @JsonProperty(value = "paymentId", access = JsonProperty.Access.READ_ONLY)
        Long paymentId,

        @JsonProperty(value = "userId", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotBlank
        String userId,

        @JsonProperty(value = "type", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotBlank
        @NotInvalidPointTransactionType
        PointTransactionType type,

        @JsonProperty(value = "amount", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotBlank
        @Min(0)
        Long amount,

        @JsonProperty(value = "startDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime startDate,

        @JsonProperty(value = "endDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime endDate
) {
    public PointHistoryFacadeDTOParam convertToPointHistoryFacadeDTOParam() {
        return PointHistoryFacadeDTOParam.builder()
                    .build();
    }
}
