package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.domain.payment.Payment;
import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentNotFoundException;
import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateUser;
import io.dev.concertreservationsystem.domain.user.factory.SimpleUserFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final ReservationRepository reservationRepository;

    private final SimpleUserFactory simpleUserFactory;

    @Transactional
    @Validated(CreateUser.class)
        public UserDTOResult insertUser(@Valid UserDTOParam userDTOParam) {

            User user = simpleUserFactory.orderUser(UUID.randomUUID().toString(), userDTOParam.userName(), userDTOParam.age(), userDTOParam.gender());

            userRepository.createUser(user);

            return userRepository.findUserByUserId(user.getUserId())
                    .orElseThrow(() -> {
                        log.debug("When: userRepository.findUserByUserId(user.getUserId()), Action: UserNotFoundException");
                        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
                    }).convertToUserDTOResult();
    }

    public void checkUserPointBalance(UserDTOParam userDTOParam) {

        reservationRepository.findReservationsByUserIdAndPaymentId(userDTOParam.userId(), userDTOParam.paymentId()).orElseThrow(()->{
            throw new PaymentNotFoundException(ErrorCode.PAYMENT_NOT_FOUND);
        });

        Payment payment = paymentRepository.findPaymentByPaymentId(userDTOParam.paymentId());

        userRepository.findUserByUserId(userDTOParam.userId()).orElseThrow(()->{
                log.debug("When: userRepository.findUserByUserId(userDTOParam.userId()), Action: UserNotFoundException");
                throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
            }).checkPrice(payment.getTotalPrice());
    }
}
