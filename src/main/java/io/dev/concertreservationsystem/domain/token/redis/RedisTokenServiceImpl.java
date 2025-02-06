package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateToken;
import io.dev.concertreservationsystem.domain.token.*;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
@Profile(value = "redis")
public class RedisTokenServiceImpl implements TokenService{

    private final RedisTokenRepository redisTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Validated(CreateToken.class)
    public TokenDTOResult publishToken(@Valid TokenDTOParam tokenDTOParam) {
        // tokenDTOParam에 담긴 userId로 회원가입 돼있나 확인
        userRepository.findUserByUserId(tokenDTOParam.userId())
                                            .orElseThrow(()->{
                                                throw new ServiceDataNotFoundException(ErrorCode.USER_NOT_FOUND, "TOKEN SERVICE", "publishToken");
                                            });

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        Token token = Token.createTokenForRedis(tokenDTOParam.userId());

        // redis에 token 저장, tokenId 반환
        Double tokenId = redisTokenRepository.saveToken(token);

        return redisTokenRepository.findTokenByTokenId(tokenId).stream().findFirst()
                                                                        .orElseThrow(()->{
                                                                            throw new ServiceDataNotFoundException(ErrorCode.TOKEN_SAVE_FAILED, "TOKEN SERVICE", "publishToken");
                                                                        }).convertToTokenDTOResult();
    }

    @Override
    public void checkTokenStatusValidation(TokenDTOParam tokenDTOParam) {

    }

    @Override
    public void activeTokens(long maxActiveTokenLimit) {

    }

    @Override
    public void expireTokens() {

    }
}
