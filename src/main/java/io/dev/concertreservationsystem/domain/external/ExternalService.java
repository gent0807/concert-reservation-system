package io.dev.concertreservationsystem.domain.external;

import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ExternalService {

    private final ExternalRepository externalRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendPaymentData(ExternalDTOParam externalDTOParam) {
        externalRepository.sendPaymentData(externalDTOParam);

        // 외부에 결제 정보 전송 성공 이벤트 발행
    }

}
