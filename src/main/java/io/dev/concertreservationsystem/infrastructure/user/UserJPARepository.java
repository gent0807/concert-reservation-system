package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Long> {
}
