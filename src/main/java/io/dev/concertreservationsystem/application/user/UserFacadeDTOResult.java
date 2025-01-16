package io.dev.concertreservationsystem.application.user;

import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserFacadeDTOResult(
    @NotNull
    String userId,

    @NotNull
    String userName,

    @Min(0)
    Integer age,

    @NotNull
    UserGenderType gender,

    @Min(0)
    Long point,

    @NotNull
    LocalDateTime createdAt,

    @NotNull
    LocalDateTime updatedAt,

    @NotNull
    LocalDateTime deletedAt

) {
}
