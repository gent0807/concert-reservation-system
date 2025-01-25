package io.dev.concertreservationsystem.application.reservation.concert;

import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.payment.PaymentStatusType;
import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import io.dev.concertreservationsystem.interfaces.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.interfaces.common.exception.error.ServiceDataNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("pessimistic-lock")
@Slf4j
public class ConcertReservationConcurrencyTest {
    @Autowired
    ConcertReserveAdminFacade concertReserveAdminFacade;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    ConcertDetailRepository concertDetailRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PaymentRepository paymentRepository;

    private static final String TEST_USER_ID = UUID.randomUUID().toString();

    private static final long TEST_CONCERT_BASIC_ID = 1L;

    private long TEST_SEAT_ID;

    private long TEST_RESERVATION_ID;

    private long TEST_PAYMENT_ID;

    User saveUser;

    ConcertDetail saveConcertDetail;

    Seat saveSeat;

    Reservation saveReservation;

    Payment savePayment;

    @BeforeEach
    void setUp(){

        // User 저장
        saveUser = User.builder()
                .userId(TEST_USER_ID)
                .age(31)
                .point(10000L)
                .build();

        // ConcertDetail 저장
        saveConcertDetail = ConcertDetail.builder()
                .concertBasicId(TEST_CONCERT_BASIC_ID)
                .concertDetailStatus(ConcertDetailStatusType.RESERVABLE)
                .startTime(LocalDateTime.of(2025, 10, 1, 10, 0))
                .endTime(LocalDateTime.of(2025, 10, 1, 12, 0))
                .build();

        concertDetailRepository.save(saveConcertDetail);

        long TEST_CONCERT_DETAIL_ID = concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(TEST_CONCERT_BASIC_ID, ConcertDetailStatusType.RESERVABLE).orElseThrow().getFirst().getConcertDetailId();

        // Seat 저장
        saveSeat = Seat.builder()
                        .concertDetailId(TEST_CONCERT_DETAIL_ID)
                        .seatStatus(SeatStatusType.RESERVABLE)
                        .seatNumber(1)
                        .price(50000)
                        .build();

        seatRepository.save(saveSeat);

        TEST_SEAT_ID = seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(TEST_CONCERT_DETAIL_ID, SeatStatusType.RESERVABLE).orElseThrow().getFirst().getSeatId();

        // Payment 저장
        savePayment = Payment.builder()
                        .totalPrice(saveSeat.getPrice())
                        .paymentStatus(PaymentStatusType.PUBLISHED)
                        .build();

        paymentRepository.save(savePayment);

        // Reservation 저장
        Reservation reservation = Reservation.builder()
                                    .userId(TEST_USER_ID)
                                    .seatId(TEST_SEAT_ID)
                                    .paymentId(TEST_PAYMENT_ID)
                                    .build();
    }

    @Test
    @DisplayName("동일한 좌석에 대한 예약 요청 동시에 발생하는 경우, 비관적 lock이면 DomainModelParamInvalidException, 낙관적 lock이면 OptimisticLockException")
    public void 동일한_좌석에_예약_요청이_동시에_발생하는_경우_비관적_lock이면_DomainModelParamInvalidException_낙관적_lock이면_OptimisticLockException() throws InterruptedException {


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
                }catch(ServiceDataNotFoundException e){
                    return false;
                }catch (OptimisticLockException e){
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
    }


}
