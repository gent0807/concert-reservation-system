package io.dev.concertreservationsystem.domain.token;

import jakarta.validation.Valid;

public interface TokenService {
    public TokenDTOResult publishToken(@Valid TokenDTOParam tokenDTOParam);
    public void checkTokenStatusValidation(@Valid TokenDTOParam tokenDTOParam);
    public void activeTokens(long maxActiveTokenLimit);
    public void expireTokens();
}
