package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateUser;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTOParam(

        @NotBlank
        String userId,

        @NotBlank(groups = CreateUser.class)
        String userName,

        @NotBlank(groups = CreateUser.class)
        @Min(value = 0, groups = CreateUser.class)
        Integer age,

        @NotBlank(groups = CreateUser.class)
        @NotInvalidUserGenderType(groups = CreateUser.class)
        UserGenderType gender,

        Long paymentId
){
}
