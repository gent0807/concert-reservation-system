package io.dev.concertreservationsystem.interfaces.api.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateReservations;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
public class ReservationRequestDTO{

        Long reservationId;

        Long concertDetailId;

        Long seatId;

        String userId;

        Long paymentId;

        Integer reservationStatus;

        LocalDateTime createdAt;

        LocalDateTime updatedAt;

        public static List<ConcertReserveAdminDTOParam> convertToConcertReserveAdminDTOParamList(List<ReservationRequestDTO> reservationRequestDTOList) {

            return reservationRequestDTOList.stream().map(ReservationRequestDTO::convertToConcertReserveAdminDTOParam).toList();
        }

        private ConcertReserveAdminDTOParam convertToConcertReserveAdminDTOParam() {
            return ConcertReserveAdminDTOParam.builder()
                    .seatId(this.seatId)
                    .userId(this.userId)
                    .concertDetailId(this.concertDetailId)
                    .build();
        }


}
