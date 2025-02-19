package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final SeatRepository seatRepository;

    @Validated(CreateReservations.class)
    public PaymentDTOResult publishNewPayment(List<@Valid ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

        List<Integer> priceList = concertReserveAdminDTOParamList.stream().map((concertReserveAdminDTOParam)->{
            return seatRepository.findSeatBySeatId(concertReserveAdminDTOParam.seatId()).orElseThrow().getPrice();
        }).toList();

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        Payment payment = Payment.createPayment(priceList.stream().reduce(0, Integer::sum), PaymentStatusType.NOT_PAID);

        paymentRepository.savePayment(payment);

        return paymentRepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(payment.getPaymentStatus()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.PAYMENT_SAVE_FAILED, "PAYMENT SERVICE", "publishNewPayment");
        }).get(0).convertToPaymentDTOResult();


    }


    @Validated(ProcessPayment.class)
    public PaymentDTOResult updateStatusOfPayment(@Valid PaymentDTOParam paymentDTOParam) {

        Payment payment = paymentRepository.findPaymentByPaymentId(paymentDTOParam.paymentId()).orElseThrow(()->{
            throw new ServiceDataNotFoundException(ErrorCode.PAYMENT_NOT_FOUND, "PAYMENT SERVICE", "updateStatusOfPayment");
        });

        payment.setPaymentStatus(PaymentStatusType.PAID);

        paymentRepository.savePayment(payment);

        return payment.convertToPaymentDTOResult();
    }


}
