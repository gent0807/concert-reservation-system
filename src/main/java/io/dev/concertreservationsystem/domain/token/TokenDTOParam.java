package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TokenDTOParam(
        @NotBlank(groups = CheckTokenStatusValid.class)
        Long tokenId,

        @NotBlank(groups = {CreateUser.class, CheckTokenStatusValid.class})
        String userId
) {
}
