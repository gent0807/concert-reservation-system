package io.dev.concertreservationsystem.application.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserAdminDTOParam(
        Long userId,
        String userName,
        Integer age,
        String gender

) {

}
