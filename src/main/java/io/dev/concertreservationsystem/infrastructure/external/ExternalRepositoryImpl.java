package io.dev.concertreservationsystem.infrastructure.external;

import io.dev.concertreservationsystem.domain.external.ExternalDTOParam;
import io.dev.concertreservationsystem.domain.external.ExternalRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ExternalRepositoryImpl implements ExternalRepository {

    @Override
    public void sendPaymentData(ExternalDTOParam externalDTOParam) {
        log.debug("결제 정보 : ", externalDTOParam.toString());
    }
}
