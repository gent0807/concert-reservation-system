package io.dev.concertreservationsystem.infrastructure.token;

import io.dev.concertreservationsystem.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJPARepository extends JpaRepository<Token, Long> {
}
