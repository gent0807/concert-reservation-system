package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
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

    private final ConcertDetailRepository concertDetailRepository;

    private final PaymentRepository paymentRepository;

    @Validated(SearchReservableSeat.class)
    public List<SeatDTOResult> findReservableSeats(@Valid ConcertDetailDTOParam concertDetailDTOParam) {

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        Seat seat = Seat.createSeat(concertDetailDTOParam.concertDetailId(), SeatStatusType.RESERVABLE);

        return seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(seat.getConcertDetailId(), seat.getSeatStatus())
                .orElseThrow(()->{
                    throw new ServiceDataNotFoundException(ErrorCode.RESERVABLE_SEAT_NOT_FOUND, "SEAT SERVICE", "findReservableSeats");
                }).stream().map(Seat::convertToSeatDTOResult).collect(Collectors.toList());

    }

    //@Validated({CreateReservations.class, ProcessPayment.class})
    public void updateStatusOfConcertDetailAndSeats(List<SeatDTOParam> seatDTOParamList, SeatStatusType seatStatus) {
        seatDTOParamList.stream().forEach(
                seatDTOParam -> {

                    Seat seat = seatRepository.findSeatBySeatIdWithLock(seatDTOParam.seatId()).orElseThrow(
                            ()->{
                                throw new ServiceDataNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID, "SEAT SERVICE", "updateStatusOfSeats");
                            }
                    );

                    seat.updateSeatStatus(seatStatus);

                    seat.updateExpiredAt(LocalDateTime.now().plusMinutes(5));

                    seatRepository.save(seat);

                    ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailIdWithLock(seat.getConcertDetailId()).orElseThrow(
                            ()->{
                                throw new ServiceDataNotFoundException(ErrorCode.CONCERT_DETAIL_NOT_FOUND, "SEAT SERVICE", "updateStatusOfSeats");
                            }
                    );

                    concertDetail.setConcertDetailStatus(ConcertDetailStatusType.COMPLETED);

                    List<Seat> seatList = seatRepository.findSeatsByConcertDetailId(concertDetail.getConcertDetailId());

                    seatList.stream().forEach(s->{
                        if(s.getSeatStatus() == SeatStatusType.RESERVABLE){
                            concertDetail.setConcertDetailStatus(ConcertDetailStatusType.RESERVABLE);
                        }
                    });

                    concertDetailRepository.save(concertDetail);

                }
        );
    }


    @Transactional
    public void expireSeatReservation() {

        seatRepository.findSeatsBySeatStatusWithLock(SeatStatusType.OCCUPIED).orElseThrow(
            ()->{
                throw new ServiceDataNotFoundException(ErrorCode.OCCUPIED_SEAT_NOT_FOUND, "SEAT SERVICE", "expireSeatReservation");
            }
        ).stream().forEach(seat->{
            if(seat.getExpiredAt().isBefore(LocalDateTime.now())){

                Reservation reservation = reservationRepository.findReservationBySeatIdAndStatusWithLock(seat.getSeatId(), ReservationStatusType.TEMP).orElseThrow(
                        ()->{
                            throw new ServiceDataNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, "SEAT SERVICE", "expireSeatReservation");
                        }
                );

                Payment payment = paymentRepository.findPaymentByPaymentIdWithLock(reservation.getPaymentId()).orElseThrow(
                        ()->{
                            throw new ServiceDataNotFoundException(ErrorCode.PAYMENT_NOT_FOUND, "SEAT SERVICE", "expireSeatReservation");
                        }
                );

                seat.updateSeatStatus(SeatStatusType.RESERVABLE);
                seat.updateExpiredAt(null);
                seatRepository.save(seat);

                reservation.setReservationStatus(ReservationStatusType.CANCELLED);
                reservationRepository.save(reservation);

                payment.setPaymentStatus(PaymentStatusType.CANCELLED);
                paymentRepository.save(payment);
            }
        });

    }

    @Validated(ProcessPayment.class)
    public void checkSeatsOccupied(List<SeatDTOParam> seatDTOParams) {
        seatDTOParams.stream().forEach(seatDTOParam -> {
           Seat seat = seatRepository.findSeatBySeatIdWithLock(seatDTOParam.seatId()).orElseThrow(()->{
              throw new ServiceDataNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID, "SEAT SERVICE", "checkStatusOfSeats");
           });

           seat.checkOccupied();
        });
    }

    //@Validated(CreateReservations.class)
    public void checkReservableOfConcertDetailAndSeat(List<SeatDTOParam> seatDTOParamList) {

        seatDTOParamList.stream().forEach(seatDTOParam -> {

            Seat seat = seatRepository.findSeatBySeatIdWithLock(seatDTOParam.seatId()).orElseThrow(()->{
                throw new ServiceDataNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID, "CONCERT DETAIL SERVICE", "checkReservableOfConcertDetail");
            });

            ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailIdWithLock(seat.getConcertDetailId())
                    .orElseThrow(()->{
                        throw new ServiceDataNotFoundException(ErrorCode.CONCERT_DETAIL_NOT_FOUND, "CONCERT DETAIL SERVICE", "checkReservableOfConcertDetail");
                    });

            concertDetail.checkReservable();

            seat.checkReservable();

        });
    }
}
