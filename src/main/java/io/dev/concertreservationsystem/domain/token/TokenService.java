package io.dev.concertreservationsystem.domain.token;


import io.dev.concertreservationsystem.domain.token.factory.SimpleTokenFactory;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.*;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateToken;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final SimpleTokenFactory simpleTokenFactory;

    @Validated(CreateToken.class)
    @Transactional
    public TokenDTOResult publishToken(@Valid TokenDTOParam tokenDTOParam) {

        // tokenDTOParam에 담긴 userId로 회원가입 돼있나 확인
        userRepository.findUserByUserId(tokenDTOParam.userId())
                                    .orElseThrow(()->{
                                        log.debug("not found user");
                                        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                                    });

        // tokenDTOParam 이용한 Token 타입 객체 생성
        Token token = simpleTokenFactory.orderToken(tokenDTOParam.userId());

        // token 저장
        tokenRepository.saveToken(token);

        // 현재 유저의 토큰 목록 조회 후 가장 최근 토큰 반환
        return tokenRepository.findTokensByUserIdAndTokenStatusOrderByCreatedAtDesc(tokenDTOParam.userId(), TokenStatusType.INACTIVE)
                                                                .orElseThrow(()->{
                                                                    log.debug("not found token list");
                                                                    throw new TokenNotFoundException(ErrorCode.TOKEN_SAVE_FAILED);
                                                                }).get(0).convertToTokenDTOResult();

    }

    @Validated(CheckTokenStatusValid.class)
    public void checkTokenStatusValidation(@Valid TokenDTOParam tokenDTOParam) {

        // tokenDTOParam 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        Token token = tokenRepository.findByTokenIdAndUserId(tokenDTOParam.tokenId(), tokenDTOParam.userId())
                                                .orElseThrow(()-> {
                                                    log.debug("not found token");
                                                    throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
                                                });

        // token 상태 검사
        token.checkStatus();

    }
}
