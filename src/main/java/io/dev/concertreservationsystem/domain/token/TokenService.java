package io.dev.concertreservationsystem.domain.token;


import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenDTOResult publishToken(TokenDTOParam tokenDTOParam) {
        return null;
    }

    public void checkTokenStatusValidation(TokenDTOParam tokenDTOParam) {

        // tokenDTOParam을 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        Token token = tokenRepository.findByTokenIdAndUserId(tokenDTOParam.tokenId(), tokenDTOParam.userId())
                                                .orElseThrow(()-> {
                                                    log.debug("not found token");
                                                    throw new TokenInvalidException(ErrorCode.TOKEN_NOT_FOUND);
                                                });

        // token 상태 검사
        token.checkStatus();

    }
}
