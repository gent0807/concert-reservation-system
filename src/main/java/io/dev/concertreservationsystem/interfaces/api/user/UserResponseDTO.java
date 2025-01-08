package io.dev.concertreservationsystem.interfaces.api.user;


public record UserResponseDTO(
        String uuid,
        String userName,
        Integer age,
        String gender,
        Integer point
){

}
