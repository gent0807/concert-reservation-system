package io.dev.concertreservationsystem.domain.token;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TokenRepository {
    public Optional<Token> findByTokenIdAndUserId(Long tokenId, String userId);

    public void saveToken(Token token);
}
