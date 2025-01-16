package io.dev.concertreservationsystem.interfaces.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.user.UserFacadeDTOParam;
import io.dev.concertreservationsystem.interfaces.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(

    @JsonProperty("userName")
    @NotBlank
    String userName,

    @JsonProperty("age")
    @NotBlank
    @Min(0)
    Integer age,

    @JsonProperty("gender")
    @NotBlank
    @NotInvalidUserGenderType
    UserGenderType gender
) {
    public UserFacadeDTOParam convertToUserFacadeDTOParam() {
        return UserFacadeDTOParam.builder()
                .userName(this.userName)
                .age(this.age)
                .gender(this.gender)
                .build();
    }
}
