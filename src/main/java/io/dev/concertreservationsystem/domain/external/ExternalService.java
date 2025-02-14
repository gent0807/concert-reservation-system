package io.dev.concertreservationsystem.domain.external;

import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalService {

    private final ExternalRepository externalRepository;

    public void sendPaymentData(PaymentDTOResult paymentDTOResult) {
        externalRepository.sendPaymentData(paymentDTOResult);
    }

}
