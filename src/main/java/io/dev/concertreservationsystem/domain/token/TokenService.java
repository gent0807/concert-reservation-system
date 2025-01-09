package io.dev.concertreservationsystem.domain.token;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenDTOResult publishToken(TokenDTOParam tokenDTOParam) {
        return null;
    }

    public void checkTokenStatusValidation(TokenDTOParam tokenDTOParam) {

        // tokenDTOParam을 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        Token token = tokenRepository.findByTokenIdAndUserId(tokenDTOParam.tokenId(), tokenDTOParam.userId())
                                                .orElseThrow(()-> new RuntimeException("존재하지 않는 토큰입니다. 유효하지 않습니다."));

        // token 상태 검사
        token.checkStatus();

    }
}
