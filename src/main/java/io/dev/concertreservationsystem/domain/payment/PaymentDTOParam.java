package io.dev.concertreservationsystem.domain.payment;


import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentDTOParam(
        String userId,

        @NotNull(groups = ProcessPayment.class)
        @Min(value= 0, groups = ProcessPayment.class)
        Long paymentId
) {

}
