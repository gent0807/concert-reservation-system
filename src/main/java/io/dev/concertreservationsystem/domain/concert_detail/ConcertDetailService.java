package io.dev.concertreservationsystem.domain.concert_detail;

import io.dev.concertreservationsystem.domain.concert_detail.factory.ReservableConcertDetailFactory;
import io.dev.concertreservationsystem.domain.reservation.ReservationDTOParam;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatService;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ConcertDetailNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.SearchReservableConcertDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class ConcertDetailService {

    private final ConcertDetailRepository concertDetailRepository;

    private final SeatRepository seatRepository;

    private final ReservableConcertDetailFactory reservableConcertDetailFactory;

    @Validated(SearchReservableConcertDetail.class)
    public List<ConcertDetailDTOResult> findReservableConcertDetails(@Valid ConcertDetailDTOParam concertDetailDTOParam) {

        // ConcertDetail 타입 객체 생성
        ConcertDetail concertDetail = reservableConcertDetailFactory.orderConcertDetail(concertDetailDTOParam.concertBasicId());

        return concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertDetail.getConcertBasicId(), concertDetail.getConcertDetailStatus())
                                                                        .orElseThrow(()->{
                                                                            log.debug( "Reservable ConcertDetail Not Found");
                                                                            throw new ConcertDetailNotFoundException(ErrorCode.RESERVABLE_CONCERT_DETAIL_NOT_FOUND);
                                                                        }).stream().map(ConcertDetail::convertToConcertDetailDTOResult).collect(Collectors.toList());
    }

    public void checkReservableOfConcertDetail(List<ReservationDTOParam> reservationDTOParamList) {
        reservationDTOParamList.stream().forEach(reservationDTOParam -> {

            Seat seat = seatRepository.findSeatBySeatId(reservationDTOParam.seatId());

            seat.checkReservable();

            ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailId(seat.getConcertDetailId());

            concertDetail.checkReservable();

        });
    }

    public void updateStatusOfConcertDetails(List<ReservationDTOParam> reservationDTOParamList) {
        reservationDTOParamList.stream().forEach(reservationDTOParam -> {
            Seat seat = seatRepository.findSeatBySeatId(reservationDTOParam.seatId());

            ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailId(seat.getConcertDetailId());

            List<SeatStatusType> seatStatuses = seatRepository.findSeatsByConcertDetailId(concertDetail.getConcertDetailId()).stream().map(Seat::getSeatStatus).collect(Collectors.toList());

            if(!seatStatuses.contains(SeatStatusType.RESERVABLE)){

                concertDetail.setConcertDetailStatus(ConcertDetailStatusType.COMPLETED);

                concertDetailRepository.saveConcertDetail(concertDetail);
            }
        });
    }
}
