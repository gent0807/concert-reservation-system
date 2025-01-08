package io.dev.concertreservationsystem.application.user;

import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserAdminDTOResult(
    @NotNull
    String userId,

    @NotNull
    String userName,

    @Min(0)
    Integer age,

    @NotNull
    UserGenderType gender,

    @Min(0)
    Integer point,

    @NotNull
    LocalDateTime createdAt,

    @NotNull
    LocalDateTime updatedAt,

    @NotNull
    LocalDateTime deletedAt

) {
}
