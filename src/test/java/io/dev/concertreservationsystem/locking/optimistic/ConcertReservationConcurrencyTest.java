package io.dev.concertreservationsystem.locking.optimistic;

import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminDTOParam;
import io.dev.concertreservationsystem.application.reservation.concert.ConcertReserveAdminFacade;
import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetail;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailRepository;
import io.dev.concertreservationsystem.domain.concert_detail.ConcertDetailStatusType;
import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.reservation.Reservation;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.domain.seat.Seat;
import io.dev.concertreservationsystem.domain.seat.SeatRepository;
import io.dev.concertreservationsystem.domain.seat.SeatStatusType;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
@ActiveProfiles("optimistic-lock")
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

    private static final String TEST_USER_NAME = "TESTER";

    private static final int TEST_AGE = 31;

    private static final long USER_INIT_POINT = 10000L;

    User saveUser;

    private static final long TEST_CONCERT_BASIC_ID = 1L;

    private long TEST_CONCERT_DETAIL_ID;

    ConcertDetail saveConcertDetail;

    private long TEST_SEAT_ID;

    private static final long seatPrice = 50000L;

    Seat saveSeat;

    private long TEST_PAYMENT_ID;

    Payment savePayment;

    private long TEST_RESERVATION_ID;

    Reservation saveReservation;

    @BeforeEach
    void setUp(){

        // User 저장
        User user = User.builder()
                .userId(TEST_USER_ID)
                .userName(TEST_USER_NAME)
                .gender(UserGenderType.MALE)
                .age(TEST_AGE)
                .point(USER_INIT_POINT)
                .build();

        userRepository.save(user);

        saveUser = userRepository.findUserByUserId(user.getUserId()).orElseThrow(()->{
            log.debug("finding user of user-id [{}] is fail", user.getUserId());
            return null;
        });

        log.debug("saveUser ID: {}", saveUser.getUserId());

        // ConcertDetail 저장
        ConcertDetail concertDetail = ConcertDetail.builder()
                .concertBasicId(TEST_CONCERT_BASIC_ID)
                .concertDetailStatus(ConcertDetailStatusType.RESERVABLE)
                .startTime(LocalDateTime.of(2025, 10, 1, 10, 0))
                .endTime(LocalDateTime.of(2025, 10, 1, 12, 0))
                .build();

        concertDetailRepository.save(concertDetail);

        saveConcertDetail = concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertDetail.getConcertBasicId(), ConcertDetailStatusType.RESERVABLE).orElseThrow(()->{
            log.debug("finding concertDetail of concert-basic-id [{}] is fail", concertDetail.getConcertBasicId());
            return null;
        }).getFirst();

        TEST_CONCERT_DETAIL_ID = saveConcertDetail.getConcertDetailId();

        log.debug("saveConcertDetail ID: {}", saveConcertDetail.getConcertDetailId());

        // Seat 저장
        Seat seat = Seat.builder()
                        .concertDetailId(TEST_CONCERT_DETAIL_ID)
                        .seatStatus(SeatStatusType.RESERVABLE)
                        .seatNumber(1)
                        .price(seatPrice)
                        .build();

        seatRepository.save(seat);

        saveSeat = seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(TEST_CONCERT_DETAIL_ID, SeatStatusType.RESERVABLE).orElseThrow(()->{
            log.debug("finding reservable seat of concert-detail-id [{}] is fail", seat.getConcertDetailId());
            return null;
        }).getFirst();

        TEST_SEAT_ID = saveSeat.getSeatId();

        log.debug("saveSeat ID: {}", saveSeat.getSeatId());

        /*
        // Payment 저장
        Payment payment = Payment.builder()
                        .totalPrice(saveSeat.getPrice())
                        .paymentStatus(PaymentStatusType.PUBLISHED)
                        .build();

        paymentRepository.save(payment);

        savePayment = paymentRepository.findPaymentsByPaymentStatusOrderByCreatedAtDesc(PaymentStatusType.PUBLISHED).orElseThrow(()->{
            log.debug("finding payment of payment-status [{}] is fail", payment.getPaymentStatus());
            return null;
        }).getFirst();

        TEST_PAYMENT_ID = savePayment.getPaymentId();

        // Reservation 저장
        Reservation reservation = Reservation.builder()
                                    .userId(TEST_USER_ID)
                                    .seatId(TEST_SEAT_ID)
                                    .paymentId(TEST_PAYMENT_ID)
                                    .build();

        reservationRepository.save(reservation);

        saveReservation = reservationRepository.findReservationByUserIdAndSeatIdAndPaymentId(TEST_USER_ID, TEST_SEAT_ID, TEST_PAYMENT_ID).orElseThrow(()->{
            log.debug("finding reservation of user-id [{}], seat-id [{}], payment-id [{}] is fail", TEST_USER_ID, TEST_SEAT_ID, TEST_PAYMENT_ID);
            return null;
        });

        TEST_RESERVATION_ID = saveReservation.getReservationId();
        */
    }

    @Test
    @DisplayName("동일한 좌석에 대한 예약 요청 동시에 발생하는 경우, 비관적 lock이면 DomainModelParamInvalidException, 낙관적 lock이면 ObjectOptimisticLockingFailureException")
    public void 동일한_좌석에_예약_요청이_동시에_발생하는_경우_비관적_lock이면_DomainModelParamInvalidException_낙관적_lock이면_OptimisticLockException() throws InterruptedException {

        long startTime;

        long endTime;

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
                try{
                    latch.countDown();
                    latch.await();

                    concertReserveAdminFacade.insertReservations(concertReserveAdminDTOParamList);

                    return true;

                }catch (DomainModelParamInvalidException e){

                    return false;

                }catch(ServiceDataNotFoundException e){

                    return false;
                }catch (ObjectOptimisticLockingFailureException e){

                    return false;
                }

            }));
        }

        startTime = System.currentTimeMillis();

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        endTime = System.currentTimeMillis();

        // 결과 확인
        long successCount = results.stream().filter(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                return false;
            }
        }).count();

        /*
        long failCount = results.stream().filter(future->{
            try {
                return !future.get();
            } catch (Exception e) {
                return false;
            }
        }).count();
         */

        // 동시성 테스트 결과 검증
        assertThat(successCount).isEqualTo(1); // 한 요청만 성공해야 함
        //assertThat(failCount).isEqualTo(threadCount-1);

        log.debug("실행 시간 : {} ms", endTime - startTime);
    }
}
