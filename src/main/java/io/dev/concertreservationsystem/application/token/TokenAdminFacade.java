package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import io.dev.concertreservationsystem.domain.token.TokenService;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class TokenAdminFacade {

    private final TokenService tokenService;

    @Validated(CreateToken.class)
    public TokenAdminDTOResult publishToken(@Valid TokenAdminDTOParam tokenAdminDTOParam){

            // tokenDTOParam의 userId를 이용하여 유저의 회원가입 여부를 조회하고, 유저의 대기열 토큰을 발급하는,
            // 현재 참조된  tokenService publishToken 메소드 호출
            TokenDTOResult tokenDTOResult = tokenService.publishToken(tokenAdminDTOParam.convertToTokenDTOParam());

            return tokenDTOResult.convertToTokenAdminDTOResult();

    }

    @Validated(CheckTokenStatusValid.class)
    public void checkTokenStatusValidation(@Valid TokenAdminDTOParam tokenAdminDTOParam) {

        tokenService.checkTokenStatusValidation(tokenAdminDTOParam.convertToTokenDTOParam());

    }
}
