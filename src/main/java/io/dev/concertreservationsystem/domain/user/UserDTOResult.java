package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.application.user.UserAdminDTOResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDTOResult(
        @NotNull
        @NotBlank
        String userId,

        @NotNull
        @NotBlank
        String userName,

        @NotNull
        @NotBlank
        @Min(0)
        Integer age,

        @NotNull
        @NotBlank
        UserGenderType gender,

        @NotNull
        @NotBlank
        @Min(0)
        @Max(500_000_000)
        Integer point,

        @NotNull
        @NotBlank
        LocalDateTime createdAt,

        @NotNull
        @NotBlank
        LocalDateTime updatedAt,

        LocalDateTime deletedAt
) {
    public UserAdminDTOResult convertToUserAdminDTOResult() {
        return UserAdminDTOResult.builder()
                .userId(this.userId)
                .userName(this.userName)
                .age(this.age)
                .gender(this.gender)
                .point(this.point)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .deletedAt(this.deletedAt)
                .build();
    }
}
