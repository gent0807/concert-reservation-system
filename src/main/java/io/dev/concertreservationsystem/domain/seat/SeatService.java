package io.dev.concertreservationsystem.domain.seat;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailDTOParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    public List<SeatDTOResult> findReservableSeats(ConcertDetailDTOParam concertDetailDTOParam) {
        return null;
    }
}
