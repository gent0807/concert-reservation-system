package io.dev.concertreservationsystem.domain.token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    public Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId);

    public void saveToken(Token token);

    public Optional<List<Token>> findTokensByUserIdAndTokenStatusOrderByCreatedAtDesc(String userId, TokenStatusType tokenStatusType);
}
