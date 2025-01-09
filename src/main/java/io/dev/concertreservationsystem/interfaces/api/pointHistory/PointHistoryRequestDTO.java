package io.dev.concertreservationsystem.interfaces.api.pointHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.pointHistory.PointHistoryAdminDTOParam;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record PointHistoryRequestDTO(

        @JsonProperty(value = "pointHistoryId", access = JsonProperty.Access.READ_ONLY)
        Long pointHistoryId,

        @JsonProperty(value = "paymentId", access = JsonProperty.Access.READ_ONLY)
        Long paymentId,

        @JsonProperty(value = "userId", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        @NotBlank
        String userId,

        @JsonProperty(value = "type", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        @NotBlank
        PointTransactionType type,

        @JsonProperty(value = "amount", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        @NotBlank
        @Min(0)
        Long amount,

        @JsonProperty(value = "startDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime startDate,

        @JsonProperty(value = "endDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime endDate
) {
    public PointHistoryAdminDTOParam convertToPointHistoryAdminDTOParam() {
        return PointHistoryAdminDTOParam.builder()
                    .build();
    }
}
