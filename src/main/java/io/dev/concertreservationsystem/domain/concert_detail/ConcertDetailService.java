package io.dev.concertreservationsystem.domain.concert_detail;


import io.dev.concertreservationsystem.domain.reservation.ReservationDTOParam;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.common.validation.interfaces.SearchReservableConcertDetail;
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

    private final ReservationRepository reservationRepository;

    @Validated(SearchReservableConcertDetail.class)
    public List<ConcertDetailDTOResult> findReservableConcertDetails(@Valid ConcertDetailDTOParam concertDetailDTOParam) {

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        ConcertDetail concertDetail = ConcertDetail.createConcertDetail(concertDetailDTOParam.concertBasicId(), ConcertDetailStatusType.RESERVABLE);

        return concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertDetail.getConcertBasicId(), concertDetail.getConcertDetailStatus())
                                                                        .orElseThrow(()->{
                                                                            throw new ServiceDataNotFoundException(ErrorCode.RESERVABLE_CONCERT_DETAIL_NOT_FOUND, "CONCERT DETAIL SERVICE", "findReservableConcertDetails");
                                                                        }).stream().map(ConcertDetail::convertToConcertDetailDTOResult).collect(Collectors.toList());
    }


}
