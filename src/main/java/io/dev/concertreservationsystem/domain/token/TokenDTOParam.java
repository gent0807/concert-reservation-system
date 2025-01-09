package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateToken;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TokenDTOParam(
        @NotNull(groups = CheckTokenStatusValid.class)
        @NotBlank(groups = CheckTokenStatusValid.class)
        Long tokenId,

        @NotNull(groups = {CreateToken.class, CheckTokenStatusValid.class})
        @NotBlank(groups = {CreateUser.class, CheckTokenStatusValid.class})
        String userId
) {
}
