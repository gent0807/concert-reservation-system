package io.dev.concertreservationsystem.domain.token.factory;

import io.dev.concertreservationsystem.domain.token.Token;
import org.springframework.stereotype.Component;

@Component
public class SimpleTokenFactory extends TokenFactory {
    @Override
    public Token createToken(String userId){
        return Token.builder()
                .userId(userId)
                .build();
    }
}
