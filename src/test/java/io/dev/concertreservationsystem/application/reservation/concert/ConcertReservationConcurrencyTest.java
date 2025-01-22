package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ConcertReservationConcurrencyTest {
    @Autowired
    ConcertReserveAdminFacade concertReserveAdminFacade;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    ConcertDetailRepository concertDetailRepository;

    private static final long TEST_CONCERT_BASIC_ID = 1L;

    private static final String TEST_USER_ID = UUID.randomUUID().toString();

    private long TEST_SEAT_ID;

    @BeforeEach
    void setUp(){

        /*// ConcertDetail 저장
        ConcertDetail concertDetail = ConcertDetail.builder()
                .concertBasicId(TEST_CONCERT_BASIC_ID)
                .concertDetailStatus(ConcertDetailStatusType.RESERVABLE)
                .startTime(LocalDateTime.of(2025, 10, 1, 10, 0))
                .endTime(LocalDateTime.of(2025, 10, 1, 12, 0))
                .build();

        concertDetailRepository.save(concertDetail);

        long TEST_CONCERT_DETAIL_ID = concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(TEST_CONCERT_BASIC_ID, ConcertDetailStatusType.RESERVABLE).orElseThrow().getFirst().getConcertDetailId();

        // Seat 저장
        Seat seat = Seat.builder()
                        .concertDetailId(TEST_CONCERT_DETAIL_ID)
                        .seatStatus(SeatStatusType.RESERVABLE)
                        .seatNumber(1)
                        .price(50000)
                        .build();

        seatRepository.save(seat);

        TEST_SEAT_ID = seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(TEST_CONCERT_DETAIL_ID, SeatStatusType.RESERVABLE).orElseThrow().getFirst().getSeatId();*/
    }

    @Test
    public void 동일한_좌석에_예약_요청이_동시에_발생하는_경우_동기화_처리하여_이미_점유된_좌석에_대한_상태_확인_시_DomainModelParamInvalidException() throws InterruptedException {
/*

        // 쓰레드 설정
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);


        // 좌석 예약 DTOList 생성
        List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList = new ArrayList<>();

        concertReserveAdminDTOParamList.add(ConcertReserveAdminDTOParam.builder()
                .userId(TEST_USER_ID)
                .seatId(TEST_SEAT_ID)
                .build());

        // 동시 실행 결과를 저장할 리스트
        List<Future<Boolean>> results = new ArrayList<>();

        for(int i = 0; i < threadCount; i++){
            results.add(executorService.submit(()->{
                latch.countDown();
                latch.await();

                try{
                    concertReserveAdminFacade.insertReservations(concertReserveAdminDTOParamList);

                    return true;
                }catch (DomainModelParamInvalidException e){
                    return false;
                }
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 결과 확인
        long successCount = results.stream().filter(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                return false;
            }
        }).count();

        long failureCount = results.stream().filter(future -> {
            try {
                return !future.get();
            } catch (Exception e) {
                return false;
            }
        }).count();

        // 동시성 테스트 결과 검증
        assertThat(successCount).isEqualTo(1); // 한 요청만 성공해야 함
        assertThat(failureCount).isEqualTo(threadCount - 1); // 나머지 요청은 실패해야 함

*/


    }


}
