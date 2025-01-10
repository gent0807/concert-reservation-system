package io.dev.concertreservationsystem.interfaces.api.payment;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOResult;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.interfaces.api.concert_basic.ConcertBasicRequestDTO;
import io.dev.concertreservationsystem.interfaces.api.concert_basic.ConcertBasicResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name="결제 API(과제)", description="주문된 콘서트 예약에 대한 결제 api입니다.")
public class PaymentController {

    private final ConcertReserveAdminFacade concertReserveAdminFacade;

    @GetMapping("{user-id}/all")
    @Operation(summary = "유저 결제 정보 전체 목록 조회 ", description = "유저의 결제 정보 전체 목록을 조회합니다.")
    public ResponseEntity<List<Payment>> findAllUserPayments (@PathVariable("user-id") Long userId)  {


        return ResponseEntity.ok().build();
    }


    @GetMapping("{user-id}/condition/{start-date}/{end-date}")
    @Operation(summary = "날짜 조건에 따른 유저 결제 정보 목록 조회 ", description = "날짜 조건에 따른 유저의 결제 정보 목록을 조회합니다.")
    public ResponseEntity<List<Payment>> findUserPaymentsByStartDateAndEndDate(@PathVariable("user-id") Long userId,
                                                                               @PathVariable("start-date") LocalDateTime startDate,
                                                                               @PathVariable("end-date") LocalDateTime endDate)  {


        return ResponseEntity.ok().build();
    }

    @PutMapping("{payment-id}")
    @Operation(summary = "결제 처리", description = "유저의 결제를 처리하고, 결제 정보를 수정 저장합니다.")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable("payment-id") Long paymentId, @RequestHeader("X-Custom-UserId") String userId)  {



            // concertReserveAdminDTOParam을 이용하여, 결제 정보를 처리하고, 예약 정보를 변경하고, 좌석 정보를 변경하고, 콘서트 실제 공연의 정보를 변경하는
            // 현재 참조된 ConcertReserveAdminFacade 타입 객체의 payAndReserveConcertSeats 메소드 호출
            ConcertReserveAdminDTOResult concertReserveAdminDTOResult =  concertReserveAdminFacade.payAndReserveConcertSeats( ConcertReserveAdminDTOParam.builder()
                                                                                                                                                        .paymentId(paymentId)
                                                                                                                                                        .userId(userId)
                                                                                                                                                        .build());


            return ResponseEntity.ok().body(concertReserveAdminDTOResult.convertToPaymentResponseDTO());





    }

}
