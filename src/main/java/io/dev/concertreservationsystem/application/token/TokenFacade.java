package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import io.dev.concertreservationsystem.domain.token.TokenService;
import io.dev.concertreservationsystem.domain.token.TokenServiceImpl;
import io.dev.concertreservationsystem.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class TokenFacade {

    private final TokenService tokenService;

    @Validated(CreateToken.class)
    public TokenFacadeDTOResult publishToken(@Valid TokenFacadeDTOParam tokenFacadeDTOParam){

            // tokenDTOParam의 userId를 이용하여 유저의 회원가입 여부를 조회하고, 유저의 대기열 토큰을 발급하는,
            // 현재 참조된  tokenService publishToken 메소드 호출
            TokenDTOResult tokenDTOResult = tokenService.publishToken(tokenFacadeDTOParam.convertToTokenDTOParam());

            return tokenDTOResult.convertToTokenFacadeDTOResult();

    }

    // 비활성화 상태인 유저 대기열 토큰들중 상위 10개 활성 상태로
    public void activeTokens(long maxActiveLimit) {
        tokenService.activeTokens(maxActiveLimit);
    }

    // 활성화 상태인 유저 대기열 토큰들 중 만료 시각이 지난 토큰들 만료 상태로
    public void expiredTokens() {
        tokenService.expireTokens();
    }

    @Validated(CheckTokenStatusValid.class)
    public void checkTokenStatusValidation(@Valid TokenFacadeDTOParam tokenFacadeDTOParam) {
        tokenService.checkTokenStatusValidation(tokenFacadeDTOParam.convertToTokenDTOParam());
    }


}
