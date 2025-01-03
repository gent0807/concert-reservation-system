package io.dev.concertreservationsystem.interfaces.api.member;


import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberRequestDTO(
    @JsonProperty("memberId") String memberId,
    @JsonProperty("memberName") String memberName,
    @JsonProperty("age") Integer age,
    @JsonProperty("gender") String gender,
    @JsonProperty("balance") Integer balance
) {
}
