package io.dev.concertreservationsystem.interfaces.api.user;


public record UserResponseDTO(
        String uuid,
        String memberName,
        Integer age,
        String gender,
        Integer balance
){

}
