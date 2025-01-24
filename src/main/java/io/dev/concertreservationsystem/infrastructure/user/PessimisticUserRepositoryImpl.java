package io.dev.concertreservationsystem.infrastructure.user;


import io.dev.concertreservationsystem.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("pessimistic-lock")
public class PessimisticUserRepositoryImpl implements UserRepository {

    private final PessimisticUserRepository pessimisticUserRepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return pessimisticUserRepository.findUserByUserIdForUpdate(userId);
    }


    @Override
    public void createUser(User user) {
        pessimisticUserRepository.save(user);
    }


    @Override
    public Optional<User> findUserByUserId(String userId){
        return pessimisticUserRepository.findUserByUserId(userId);
    }

    @Override
    public User saveUser(User user){
        return pessimisticUserRepository.save(user);
    }

}
