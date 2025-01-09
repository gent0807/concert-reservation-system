package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import io.dev.concertreservationsystem.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAdminFacade {

    private final TokenService tokenService;

    public TokenAdminDTOResult publishToken(TokenAdminDTOParam tokenAdminDTOParam){


            // tokenDTOParam의 userId를 이용하여 유저의 회원가입 여부를 조회하고, 유저의 대기열 토큰을 발급하는,
            // 현재 참조된  tokenService의 publishToken 메소드 호출
            TokenDTOResult tokenDTOResult = tokenService.publishToken(tokenAdminDTOParam.convertToTokenDTOParam());

            return tokenDTOResult.convertToTokenAdminDTOResult();

    }

    public void checkTokenStatusValidation(TokenAdminDTOParam tokenAdminDTOParam) {

        tokenService.checkTokenStatusValidation(tokenAdminDTOParam.convertToTokenDTOParam());

    }
}
