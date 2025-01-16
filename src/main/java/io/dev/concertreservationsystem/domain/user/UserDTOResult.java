package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.application.user.UserFacadeDTOResult;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

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
        Long point,

        @NotNull
        @NotBlank
        LocalDateTime createdAt,

        @NotNull
        @NotBlank
        LocalDateTime updatedAt,

        LocalDateTime deletedAt
) {
    public UserFacadeDTOResult convertToUserFacadeDTOResult() {
        return UserFacadeDTOResult.builder()
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
