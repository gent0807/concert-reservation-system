package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.application.common.validation.interfaces.CreateUser;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTOParam(

        @NotNull
        @NotBlank
        String userId,

        @NotNull(groups = CreateUser.class)
        @NotBlank(groups = CreateUser.class)
        String userName,

        @NotNull(groups = CreateUser.class)
        @NotBlank(groups = CreateUser.class)
        @Min(value = 0, groups = CreateUser.class)
        Integer age,

        @NotNull(groups = CreateUser.class)
        @NotBlank(groups = CreateUser.class)
        @NotInvalidUserGenderType(groups = CreateUser.class)
        UserGenderType gender
){
}
