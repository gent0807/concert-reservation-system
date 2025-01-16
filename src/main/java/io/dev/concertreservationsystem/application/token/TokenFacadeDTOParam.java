package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOParam;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateToken;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TokenFacadeDTOParam(

        @NotBlank(groups = CheckTokenStatusValid.class)
        Long tokenId,

        @NotBlank(groups = {CreateUser.class, CheckTokenStatusValid.class})
        String userId
) {
    public TokenDTOParam convertToTokenDTOParam() {
        return TokenDTOParam.builder()
                .tokenId(tokenId)
                .userId(userId)
                .build();
    }


}
