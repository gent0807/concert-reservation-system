package io.dev.concertreservationsystem.application.user;

import io.dev.concertreservationsystem.interfaces.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateUser;
import io.dev.concertreservationsystem.domain.user.UserDTOParam;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserFacadeDTOParam(

        @NotBlank
        String userId,

        @NotBlank(groups = CreateUser.class)
        String userName,

        @NotBlank(groups = CreateUser.class)
        @Min(value = 0, groups = CreateUser.class)
        Integer age,

        @NotBlank(groups = CreateUser.class)
        @NotInvalidUserGenderType(groups = CreateUser.class)
        UserGenderType gender

) {

    public UserDTOParam convertToUserDTOParam() {
        return UserDTOParam.builder()
                .userId(userId)
                .userName(userName)
                .age(age)
                .gender(gender)
                .build();

    }
}
