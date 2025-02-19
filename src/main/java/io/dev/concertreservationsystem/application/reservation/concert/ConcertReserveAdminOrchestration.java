package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.point_history.PointHistoryDTOParam;
import io.dev.concertreservationsystem.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertReserveAdminOrchestration {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ReservationService reservationService;

    // 유저 포인트 수정 이벤트 발행
    public void publishUserPointUpdateEvent(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        applicationEventPublisher.publishEvent(concertReserveAdminDTOParam.convertToPointHistoryDTOParam());
    }

    // 유저 포인트 수정 실패 이벤트 리스너
    public void userPointUpdateFailEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 유저 포인트 수정 성공 이벤트 리스너
    public void userPointUpdateSuccessEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        // 예약 정보 수정 이벤트 발행
        applicationEventPublisher.publishEvent(concertReserveAdminDTOParam.convertToReservationDTOParam());
    }

    // 예약 정보 수정 실패 이벤트 리스너
    public void reservationUpdateFailEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 예약 정보 수정 성공 이벤트 리스너
    public void reservationUpdateSuccessEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        // 결제 정보 수정 이벤트 발행
        applicationEventPublisher.publishEvent(concertReserveAdminDTOParam.convertToPaymentDTOParam());
    }

    // 결제 정보 수정 실패 이벤트 리스너
    public void paymentUpdateFailEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 결제 정보 수정 성공 이벤트 리스너
    public void paymentUpdateSuccessEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        // 좌석 정보 수정 이벤트 발행
        applicationEventPublisher.publishEvent(reservationService.convertReservationDTOParamToSeatDTOParamList(concertReserveAdminDTOParam.convertToReservationDTOParam()));
    }

    // 좌석 정보 수정 실패 이벤트 리스너
    public void seatUpdateFailEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 좌석 정보 수정 성공 이벤트 리스너
    public void seatUpdateSuccessEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {
        // 외부에 결제 정보 전송 이벤트 발행
        applicationEventPublisher.publishEvent(concertReserveAdminDTOParam.convertToExternalDTOParam());
    }

    // 외부에 결제 정보 전송 실패 이벤트 리스너
    public void paymentSendFailEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

    // 외부에 결제 정보 전송 성공 이벤트 리스너
    public void paymentSendSuccessEventListener(ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

    }

}
