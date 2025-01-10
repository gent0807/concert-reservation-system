package io.dev.concertreservationsystem.domain.payment;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.domain.payment.factory.NotPaidPaymentFactory;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateReservations;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final SeatRepository seatRepository;

    private final NotPaidPaymentFactory notPaidPaymentFactory;

    @Validated(CreateReservations.class)
    public PaymentDTOResult publishNewPayment(List<@Valid ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

        List<Integer> priceList = concertReserveAdminDTOParamList.stream().map((concertReserveAdminDTOParam)->{
            return seatRepository.findSeatBySeatId(concertReserveAdminDTOParam.seatId()).getPrice();
        }).toList();

        // Payment 타입 객체 생성
        Payment payment = notPaidPaymentFactory.orderPayment(priceList.stream().reduce(0, Integer::sum));

        paymentRepository.savePayment(payment);

        return paymentRepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(payment.getPaymentStatus()).orElseThrow(()->{
            throw new PaymentNotFoundException(ErrorCode.PAYMENT_SAVE_FAILED);
        }).get(0).convertToPaymentDTOResult();


    }

    public PaymentDTOResult updateStatusOfPayment(PaymentDTOParam paymentDTOParam) {

        Payment payment = paymentRepository.findPaymentByPaymentId(paymentDTOParam.paymentId());

        payment.setPaymentStatus(PaymentStatusType.PAID);

        paymentRepository.savePayment(payment);

        return payment.convertToPaymentDTOResult();
    }
}
