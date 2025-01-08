package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.interfaces.api.payment.PaymentResponseDTO;

import java.util.List;

public record ConcertReserveAdminDTOResult() {
    public PaymentResponseDTO convertPaymentResponseDTO(){
        return null;
    }
}
