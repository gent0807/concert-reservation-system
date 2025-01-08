package io.dev.concertreservationsystem.interfaces.api.user;


import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequestDTO(
    @JsonProperty("userName") String userName,
    @JsonProperty("age") Integer age,
    @JsonProperty("gender") String gender
) {
}
