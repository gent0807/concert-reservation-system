package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.domain.payment.PaymentRepository;
import io.dev.concertreservationsystem.domain.reservation.ReservationRepository;
import io.dev.concertreservationsystem.common.exception.error.ServiceDataNotFoundException;
import io.dev.concertreservationsystem.common.validation.interfaces.CreateUser;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final ReservationRepository reservationRepository;

    @Transactional
    @Validated(CreateUser.class)
    public UserDTOResult insertUser(@Valid UserDTOParam userDTOParam) {

        // 도메인 모델 내 정적 팩토리 메소드로 생성
        User user = User.createUser(userDTOParam.userId(), userDTOParam.userName(), userDTOParam.age(), userDTOParam.gender());

        userRepository.save(user);

        return userRepository.findUserByUserId(user.getUserId())
                .orElseThrow(() -> {
                    throw new ServiceDataNotFoundException(ErrorCode.USER_NOT_FOUND, "USER SERVICE", "insertUser()");
                }).convertToUserDTOResult();
    }


}
