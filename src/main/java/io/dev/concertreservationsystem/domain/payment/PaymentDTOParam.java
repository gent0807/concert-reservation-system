package io.dev.concertreservationsystem.domain.payment;


import lombok.Builder;

@Builder
public record PaymentDTOParam(
        String userId,
        Long paymentId
) {

}
