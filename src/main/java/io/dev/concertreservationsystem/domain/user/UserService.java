package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.application.common.validation.interfaces.CreateUser;
import io.dev.concertreservationsystem.domain.user.factory.SimpleUserFactory;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

    private final UserRepository userRepository;

    private final SimpleUserFactory simpleUserFactory;

    @Transactional
    @Validated(CreateUser.class)
    public UserDTOResult insertUser(@Valid UserDTOParam userDTOParam) {

        User user = simpleUserFactory.orderUser(UUID.randomUUID().toString(), userDTOParam.userName(), userDTOParam.age(), userDTOParam.gender());

        userRepository.createUser(user);

        return userRepository.findUserByUserId(user.getUserId())
                                        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND))
                                                    .convertToUserDTOResult();
    }
}
