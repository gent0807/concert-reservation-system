package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface OptimisticUserJPARepository extends UserJPARepository{

    @Override
    @Lock(LockModeType.NONE)
    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findUserByUserIdForUpdate(String userId);
}
