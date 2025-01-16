package io.dev.concertreservationsystem.domain.payment;


import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PaymentDTOParam(
        String userId,

        @NotBlank(groups = ProcessPayment.class)
        @Min(value= 0, groups = ProcessPayment.class)
        Long paymentId
) {

}
