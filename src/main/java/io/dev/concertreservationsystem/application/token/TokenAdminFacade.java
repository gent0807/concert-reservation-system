package io.dev.concertreservationsystem.application.token;

import io.dev.concertreservationsystem.domain.token.TokenDTOParam;
import io.dev.concertreservationsystem.domain.token.TokenDTOResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAdminFacade {

    // private final TokenService tokenService;

    public void publishToken(TokenAdminDTOParam tokenAdminDTOParam){
       /*
            // tokenAdminDTOParam을 tokenDTOParam으로 변환
            TokenDTOParam tokenDTOParam = tokenAdminDTOParam.convertToTokenDTOParam();

            // tokenDTOParam의 userId를 이용하여 유저의 회원가입 여부를 조회하고, 유저의 대기열 토큰을 발급하는,
            // 현재 참조된  tokenService의 publishToken 메소드 호출
            TokenDTOResult tokenDTOResult = tokenService.publishToken(tokenDTOParam);

            // tokenDTOResult를 tokenAdminDTOResult로 변환
            TokenAdminDTOResult  tokenAdminDTOResult = tokenDTOResult.convertToTokenAdminDTOResult();

            return tokenAdminDTOResult;
        */
    }
}
