package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.common.aop.redis.DistributedLock;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOResult;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailService;
import io.dev.concertreservationsystem.domain.external.ExternalService;
import io.dev.concertreservationsystem.domain.payment.PaymentDTOResult;
import io.dev.concertreservationsystem.domain.payment.PaymentService;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import io.dev.concertreservationsystem.domain.point_history.PointHistoryService;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOResult;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import io.dev.concertreservationsystem.domain.seat.SeatDTOParam;
import io.dev.concertreservationsystem.domain.seat.SeatDTOResult;
import io.dev.concertreservationsystem.domain.seat.SeatService;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import io.dev.concertreservationsystem.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.common.validation.interfaces.SearchReservableConcertDetail;
import io.dev.concertreservationsystem.common.validation.interfaces.SearchReservableSeat;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class ConcertReserveAdminFacade {

    private final PointHistoryService pointHistoryService;
    private final ConcertDetailService concertDetailService;
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final ExternalService externalService;
    private final ConcertReserveAdminOrchestration concertReserveAdminOrchestration;

    // 1. 예약 가능한 콘서트 실제 공연 목록 조회
    @Validated(SearchReservableConcertDetail.class)
    public List<ConcertReserveAdminDTOResult> findReservableConcertDetails(@Valid ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

            // 콘서트의 예약 가능한 실제 공연 목록 조회
            List<ConcertDetailDTOResult> concertDetailDTOResultList = concertDetailService.findReservableConcertDetails(concertReserveAdminDTOParam.convertToConcertDetailDTOParam());

            return ConcertDetailDTOResult.convertToConcertReserveAdminDTOResultList(concertDetailDTOResultList);

    }

    // 2. 콘서트 실제 공연의 예약 가능 좌석 목록 조회
    @Validated(SearchReservableSeat.class)
    public List<ConcertReserveAdminDTOResult> findReservableSeats(@Valid ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        List<SeatDTOResult> seatDTOResultList = seatService.findReservableSeats(concertReserveAdminDTOParam.convertToConcertDetailDTOParam());

        return SeatDTOResult.convertToConcertReserveAdminDTOResultList(seatDTOResultList);
    }

    // 3. 좌석 예약 주문서 발행, 좌석 임시 점유(occupied)
    //@Validated(CreateReservations.class)
    @Transactional
    //@DistributedLock(lockNm = "reservation_lock", waitTime = 0L, leaseTime = 1000L)
    public List<ConcertReserveAdminDTOResult> insertReservations(List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

        // 콘서트 실제 공연 좌석들의 예약 상태/예약 가능 여부 확인, 예약 불가(상태가 reservable 아닌 경우)이면 exception 발생
        seatService.checkReservableOfConcertDetailAndSeat(ConcertReserveAdminDTOParam.convertToSeatDTOParamList(concertReserveAdminDTOParamList));

        // 미결제 상태의 결제 정보 신규 저장(주문서 발행)
        PaymentDTOResult paymentDTOResult = paymentService.publishNewPayment(concertReserveAdminDTOParamList);

        // 좌석들 임시 예약 정보 신규 등록
        List<ReservationDTOResult> reservationDTOResultList = reservationService.insertReservations(ConcertReserveAdminDTOParam.convertToReservationDTOParamList(concertReserveAdminDTOParamList, paymentDTOResult));

        // 좌석들의 예약 상태/예약 가능 여부를 점유 상태로 수정
        seatService.updateStatusOfConcertDetailAndSeats(reservationDTOResultList.stream().map(reservationDTOResult -> {
            return SeatDTOParam.builder().seatId(reservationDTOResult.seatId()).build();
        }).collect(Collectors.toList()), SeatStatusType.OCCUPIED);



        return ReservationDTOResult.convertToConcertReserveAdminDTOResultList(reservationDTOResultList);

    }

    // 4. 주문 금액 결제, 좌석 완전 예약
    @Validated(ProcessPayment.class)
    //@Transactional
    public ConcertReserveAdminDTOResult payAndReserveConcertSeats(@Valid ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

            // 결제하려는 예약 좌석들의 상태가 점유 상태인지 확인
            seatService.checkSeatsOccupied(reservationService.convertReservationDTOParamToSeatDTOParamList(concertReserveAdminDTOParam.convertToReservationDTOParam()));

            // 좌석 예약 정보들의 상태가 임시 상태인지 확인
            reservationService.checkReservationsTemp(concertReserveAdminDTOParam.convertToReservationDTOParam());

            // 결제 정보의 상태가 미결제 상태인지 확인
            paymentService.checkPaymentPublished(concertReserveAdminDTOParam.convertToPaymentDTOParam());

            //concertReserveAdminOrchestration.publishUserPointUpdateEvent(concertReserveAdminDTOParam);

            // 유저의 포인트 차감
            pointHistoryService.useUserPoint(concertReserveAdminDTOParam.convertToPointHistoryDTOParam());

            // 좌석 예약 정보들 예약 상태를 예약 완료 상태로 변경
            reservationService.updateStatusOfReservations(concertReserveAdminDTOParam.convertToReservationDTOParam(), ReservationStatusType.CONFIRMED);

            // 결제 정보 결제 완료 상태로 변경
            PaymentDTOResult paymentDTOResult = paymentService.updateStatusOfPayment(concertReserveAdminDTOParam.convertToPaymentDTOParam(), PaymentStatusType.PAID);

            // 콘서트 실제 공연 좌석들 예약 상태를 reserved 상태로 변경
            seatService.updateStatusOfConcertDetailAndSeats(reservationService.convertReservationDTOParamToSeatDTOParamList(concertReserveAdminDTOParam.convertToReservationDTOParam()), SeatStatusType.RESERVED);

            // 외부에 결제 정보 전달
            //externalService.sendPaymentData(paymentDTOResult);

            return paymentDTOResult.convertToConcertReserveAdminDTOResult();
    }

    // 5. 좌석 점유 취소, 주문 취소
    public void cancelOccupiedConcertSeatsAndOrder(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 6. 좌석 완전 예약 취소, 결제 취소, 환불 요청
    public void cancelReservedConcertSeatsAndRefund(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 좌석 점유 시각이 지난 좌석들 상태 occupied에서 reservable로
    public void expireSeatReservation() {
        seatService.expireSeatReservation();
    }
}
