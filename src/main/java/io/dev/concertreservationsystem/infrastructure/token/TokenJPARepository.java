package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenJPARepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId);

    Optional<List<Token>> findTokensByUserIdAndTokenStatusOrderByCreatedAtAsc(String userId, TokenStatusType tokenStatus);
}
