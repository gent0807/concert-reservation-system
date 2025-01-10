package io.dev.concertreservationsystem.infrastructure.reservation;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJPARepository reservationJPARepository;

    @Override
    public void saveReservation(Reservation reservation){
        reservationJPARepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId){
        return reservationJPARepository.findReservationByUserIdAndSeatIdAndPaymentId(userId, seatId, paymentId);
    }

    @Override
    public Optional<List<Reservation>> findReservationsByUserIdAndPaymentId(String userId, Long paymentId){
        return reservationJPARepository.findReservationsByUserIdAndPaymentId(userId, paymentId);
    }
}
