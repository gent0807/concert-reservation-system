package io.dev.concertreservationsystem.domain.token;


import io.dev.concertreservationsystem.domain.token.factory.SimpleTokenFactory;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final SimpleTokenFactory simpleTokenFactory;

    public TokenDTOResult publishToken(TokenDTOParam tokenDTOParam) {

        // tokenDTOParam에 담긴 userId로 회원가입 돼있나 확인
        userRepository.findUserByUserId(tokenDTOParam.userId())
                                    .orElseThrow(()->{
                                        log.debug("not found user");
                                        throw new UserInvalidException(ErrorCode.USER_NOT_FOUND);
                                    });

        // tokenDTOParam 이용한 Token 타입 객체 생성
        Token token = simpleTokenFactory.orderToken(tokenDTOParam.userId());

        tokenRepository.saveToken(token);

        return null;
    }

    public void checkTokenStatusValidation(TokenDTOParam tokenDTOParam) {

        // tokenDTOParam 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        Token token = tokenRepository.findByTokenIdAndUserId(tokenDTOParam.tokenId(), tokenDTOParam.userId())
                                                .orElseThrow(()-> {
                                                    log.debug("not found token");
                                                    throw new TokenInvalidException(ErrorCode.TOKEN_NOT_FOUND);
                                                });

        // token 상태 검사
        token.checkStatus();

    }
}
