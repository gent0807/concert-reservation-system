package io.dev.concertreservationsystem.domain.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Token {

    @Id
    private Long tokenId;
}
