package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenRepository;
import io.dev.concertreservationsystem.domain.token.TokenStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "!redis")
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJPARepository tokenJPARepository;

    @Override
    public Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId) {
        return tokenJPARepository.findByTokenIdAndUserId(tokenId, userId);
    }

    @Override
    public void saveToken(Token token){
        tokenJPARepository.save(token);
    }

    @Override
    public Optional<List<Token>> findTokensByUserIdAndTokenStatusOrderByCreatedAtDesc(String userId, TokenStatusType tokenStatusType) {
        return tokenJPARepository.findTokensByUserIdAndTokenStatusOrderByCreatedAtAsc(userId, tokenStatusType);
    }

    @Override
    public List<Token> findInactiveTokensOrderByCreatedAtDescLimit(long maxActiveTokenLimit){
        return tokenJPARepository.findTopNTokensByTokenStatusOrderByCreatedAtDesc(TokenStatusType.INACTIVE, maxActiveTokenLimit);
    }

    @Override
    public List<Token> findAllActiveTokens(){
        return tokenJPARepository.findAllByTokenStatus(TokenStatusType.ACTIVE);
    }
}
