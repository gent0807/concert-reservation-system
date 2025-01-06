package io.dev.concertreservationsystem.interfaces.api.member;


public record MemberResponseDTO(
        String uuid,
        String memberName,
        Integer age,
        String gender,
        Integer balance
){

}
