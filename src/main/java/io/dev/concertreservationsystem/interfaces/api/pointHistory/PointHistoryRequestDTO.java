package io.dev.concertreservationsystem.interfaces.api.pointHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record PointHistoryRequestDTO(
        @JsonProperty(value = "userId", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long userId,

        @JsonProperty(value = "type", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        PointTransactionType type,

        @JsonProperty(value = "amount", required = true, access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long amount,

        @JsonProperty(value = "startDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime startDate,

        @JsonProperty(value = "endDate", access = JsonProperty.Access.READ_ONLY)
        LocalDateTime endDate
) {
}
