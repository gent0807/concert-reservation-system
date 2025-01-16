package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.token.TokenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenJPARepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId);

    Optional<List<Token>> findTokensByUserIdAndTokenStatusOrderByCreatedAtAsc(String userId, TokenStatusType tokenStatus);

    @Query(value = "SELECT * FROM token AS t WHERE t.token_status = :tokenStatus ORDER BY t.created_at DESC LIMIT :maxTokenActiveLimit", nativeQuery = true)
    List<Token> findTopNTokensByTokenStatusOrderByCreatedAtDesc(@Param("tokenStatus") TokenStatusType tokenStatus,@Param("maxTokenActiveLimit") long maxTokenActiveLimit);

    List<Token> findAllByTokenStatus(TokenStatusType tokenStatus);
}
