package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PessimisticUserRepository extends UserJPARepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findUserByUserIdForUpdate(String userId);

    Optional<User> findUserByUserId(String userId);

}
