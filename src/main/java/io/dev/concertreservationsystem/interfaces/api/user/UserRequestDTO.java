package io.dev.concertreservationsystem.interfaces.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.user.UserAdminDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.annotation.NotInvalidUserGenderType;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(

    @JsonProperty("userName")
    @NotNull
    @NotBlank
    String userName,

    @JsonProperty("age")
    @NotNull
    @NotBlank
    @Min(0)
    Integer age,

    @JsonProperty("gender")
    @NotNull
    @NotBlank
    @NotInvalidUserGenderType
    UserGenderType gender
) {
    public UserAdminDTOParam convertToUserAdminDTOParam() {
        return UserAdminDTOParam.builder()
                .userName(this.userName)
                .age(this.age)
                .gender(this.gender)
                .build();
    }
}
