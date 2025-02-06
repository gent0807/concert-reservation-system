package io.dev.concertreservationsystem.domain.token.redis;

import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.common.validation.interfaces.CheckTokenStatusValid;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateToken;
import io.dev.concertreservationsystem.domain.token.*;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

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
        Double tokenId = redisTokenRepository.saveWaitingToken(token);

        return redisTokenRepository.findWaitingTokenByTokenId(tokenId).stream().findFirst()
                                                                        .orElseThrow(()->{
                                                                            throw new ServiceDataNotFoundException(ErrorCode.TOKEN_SAVE_FAILED, "TOKEN SERVICE", "publishToken");
                                                                        }).convertToTokenDTOResult();
    }

    @Override
    public void activeTokens(long maxActiveTokenLimit) {
        Set<ZSetOperations.TypedTuple<Token>> set = redisTokenRepository.getActiveTokens(maxActiveTokenLimit);
        set.stream().forEach(token->{
            redisTokenRepository.saveActiveToken(token);
        });
    }

    @Override
    public void expireTokens() {

    }

    @Override
    @Validated(CheckTokenStatusValid.class)
    public void checkTokenStatusValidation(@Valid TokenDTOParam tokenDTOParam) {

        // tokenDTOParam 이용하여 userId와 tokenId가 일치하는 토큰이 있는지 확인
        if(!redisTokenRepository.findActiveUserByTokenId(tokenDTOParam.tokenId()).equals(tokenDTOParam.userId())){
           throw new ServiceDataNotFoundException(ErrorCode.TOKEN_NOT_FOUND, "TOKEN SERVICE", "checkTokenStatusValidation");
        }

    }


}
