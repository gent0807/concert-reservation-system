package io.dev.concertreservationsystem.domain.user;

import java.util.Optional;


public interface UserRepository {
    void createUser(User user);

    User findUserByUserIdWithLock(String userId);

    Optional<User> findUserByUserId(String userId);

    User saveUser(User user);


}
