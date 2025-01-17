package io.dev.concertreservationsystem.domain.token;


import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateToken;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Validated(CreateToken.class)
    @Transactional
    public TokenDTOResult publishToken(@Valid TokenDTOParam tokenDTOParam) {

        // tokenDTOParam에 담긴 userId로 회원가입 돼있나 확인
        userRepository.findUserByUserId(tokenDTOParam.userId())
                                    .orElseThrow(()->{
                                        throw new ServiceDataNotFoundException(ErrorCode.USER_NOT_FOUND, "TOKEN SERVICE", "publishToken");
                                    });

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        Token token = Token.createToken(tokenDTOParam.userId(), TokenStatusType.INACTIVE);

        // token 저장
        tokenRepository.saveToken(token);

        // 현재 유저의 토큰 목록 조회 후 가장 최근 토큰 반환
        return tokenRepository.findTokensByUserIdAndTokenStatusOrderByCreatedAtDesc(tokenDTOParam.userId(), TokenStatusType.INACTIVE)
                                                                .orElseThrow(()->{
                                                                    throw new ServiceDataNotFoundException(ErrorCode.TOKEN_SAVE_FAILED, "TOKEN SERVICE", "publishToken");
                                                                }).get(0).convertToTokenDTOResult();

    }

    @Validated(CheckTokenStatusValid.class)
    public void checkTokenStatusValidation(@Valid TokenDTOParam tokenDTOParam) {

        // tokenDTOParam 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        Token token = tokenRepository.findByTokenIdAndUserId(tokenDTOParam.tokenId(), tokenDTOParam.userId())
                                                .orElseThrow(()-> {
                                                    throw new ServiceDataNotFoundException(ErrorCode.TOKEN_NOT_FOUND, "TOKEN SERVICE", "checkTokenStatusValidation");
                                                });

        // token 상태 검사
        token.checkStatus();

    }

    @Transactional
    public void activeTokens(long maxActiveTokenLimit) {

        List<Token> tokenList = tokenRepository.findInactiveTokensOrderByCreatedAtDescLimit(maxActiveTokenLimit);

        tokenList.stream().forEach(token->{
            token.activeToken();
            tokenRepository.saveToken(token);
        });

    }

    public void expireTokens() {

        List<Token> tokenList = tokenRepository.findAllActiveTokens();

        tokenList.stream().forEach(token->{
            if(token.getExpiresAt().isBefore(LocalDateTime.now())){
                token.expireToken();
                tokenRepository.saveToken(token);
            }
        });
    }
}
