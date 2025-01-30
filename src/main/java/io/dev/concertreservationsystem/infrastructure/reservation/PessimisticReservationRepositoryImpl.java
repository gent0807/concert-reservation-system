package io.dev.concertreservationsystem.infrastructure.reservation;

import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile(value = "pessimistic-lock")
public class PessimisticReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJPARepository pessimisticReservationJPARepository;

    @Override
    public void save(Reservation reservation){
        pessimisticReservationJPARepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findReservationByUserIdAndSeatIdAndPaymentId(String userId, Long seatId, Long paymentId){
        return pessimisticReservationJPARepository.findReservationByUserIdAndSeatIdAndPaymentId(userId, seatId, paymentId);
    }

    @Override
    public Optional<List<Reservation>> findReservationsByUserIdAndPaymentIdWithLock(String userId, Long paymentId){
        return pessimisticReservationJPARepository.findReservationsByUserIdAndPaymentIdForUpdateWithPessimisticLock(userId, paymentId);
    }

    @Override
    public Optional<Reservation> findReservationBySeatIdAndStatusWithLock(Long seatId, ReservationStatusType reservationStatusType){
        return pessimisticReservationJPARepository.findReservationBySeatIdAndReservationStatusForUpdateWithPessimisticLock(seatId, reservationStatusType);
    }
}
