package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByUserId(String userId);


}
