package io.dev.concertreservationsystem.domain.user;

import java.util.Optional;


public interface UserRepository {

    User findUserByUserIdWithLock(String userId);

    Optional<User> findUserByUserId(String userId);

    User save(User user);

}
