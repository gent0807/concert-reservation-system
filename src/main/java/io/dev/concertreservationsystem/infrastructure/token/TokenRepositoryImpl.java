package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
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
}
