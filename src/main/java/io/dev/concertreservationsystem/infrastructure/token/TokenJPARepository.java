package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenJPARepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId);
}
