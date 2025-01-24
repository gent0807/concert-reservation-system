package io.dev.concertreservationsystem.infrastructure.user;


import io.dev.concertreservationsystem.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("pessimistic-lock")
@RequiredArgsConstructor
public class PessimisticUserRepositoryImpl implements UserRepository {

    private final PessimisticUserRepository pessimisticUserRepository;


    @Override
    public void createUser(User user) {

    }

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return pessimisticUserRepository.findUserByUserIdForUpdate(userId);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return pessimisticUserRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {
        return pessimisticUserRepository.save(user);
    }
}
