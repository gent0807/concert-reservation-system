package io.dev.concertreservationsystem.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private Long paymentId;
}
