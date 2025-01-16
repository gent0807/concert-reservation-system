package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentService;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.exception.error.PaymentInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.SeatInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.SeatNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.CreateReservations;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.ProcessPayment;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.SearchReservableSeat;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class SeatService {

    private final SeatRepository seatRepository;

    private final ReservationRepository reservationRepository;

    private final PaymentRepository paymentRepository;

    @Validated(SearchReservableSeat.class)
    public List<SeatDTOResult> findReservableSeats(@Valid ConcertDetailDTOParam concertDetailDTOParam) {

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        Seat seat = Seat.createSeat(concertDetailDTOParam.concertDetailId(), SeatStatusType.RESERVABLE);

        return seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(seat.getConcertDetailId(), seat.getSeatStatus())
                .orElseThrow(()->{
                    log.error( "reservable seat not found");
                    throw new SeatNotFoundException(ErrorCode.RESERVABLE_SEAT_NOT_FOUND);
                }).stream().map(Seat::convertToSeatDTOResult).collect(Collectors.toList());

    }

    @Validated({CreateReservations.class, ProcessPayment.class})
    public void updateStatusOfSeats(List<@Valid SeatDTOParam> seatDTOParamList, SeatStatusType seatStatus) {
        seatDTOParamList.stream().forEach(
                seatDTOParam -> {
                    Seat seat = seatRepository.findSeatBySeatId(seatDTOParam.seatId()).orElseThrow(
                            ()->{
                                log.error("seat not found");
                                throw new SeatNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID);
                            }
                    );

                    seat.updateSeatStatus(seatStatus);

                    seat.updateExpiredAt(LocalDateTime.now().plusMinutes(5));

                    seatRepository.save(seat);

                    ConcertDetail concertDetail = seatRepository.findConcertDetailBySeatId(seat.getSeatId());

                    concertDetail.setConcertDetailStatus(ConcertDetailStatusType.COMPLETED);

                    List<Seat> seatList = seatRepository.findSeatsByConcertDetailId(concertDetail.getConcertDetailId());

                    seatList.stream().forEach(s->{
                        if(s.getSeatStatus() == SeatStatusType.RESERVABLE){
                            concertDetail.setConcertDetailStatus(ConcertDetailStatusType.RESERVABLE);
                        }
                    });

                }
        );
    }


    @Transactional
    public void expireSeatReservation() {

        List<Seat> seatList = seatRepository.findSeatsBySeatStatus(SeatStatusType.OCCUPIED);

        seatList.stream().forEach(seat->{
            if(seat.getExpiredAt().isBefore(LocalDateTime.now())){
                seat.updateSeatStatus(SeatStatusType.RESERVABLE);
                seat.updateExpiredAt(null);
                seatRepository.save(seat);

                Reservation reservation = reservationRepository.findReservationBySeatId(seat.getSeatId());

                reservation.setReservationStatus(ReservationStatusType.CANCELLED);

                reservationRepository.saveReservation(reservation);

                Payment payment = paymentRepository.findPaymentByPaymentId(reservation.getPaymentId()).orElseThrow();

                payment.setPaymentStatus(PaymentStatusType.CANCELLED);

                paymentRepository.savePayment(payment);
            }
        });

    }
}
