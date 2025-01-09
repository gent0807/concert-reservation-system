package io.dev.concertreservationsystem.domain.user;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<User> findUserByUserId(String userId);

    void updateUser(User user);


}
