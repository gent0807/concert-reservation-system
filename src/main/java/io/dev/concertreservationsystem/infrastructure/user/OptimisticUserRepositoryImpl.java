package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("optimistic-lock")
@RequiredArgsConstructor
public class OptimisticUserRepositoryImpl implements UserRepository {
    private final OptimisticUserRepository optimisticUserRepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return optimisticUserRepository.findUserByUserIdForUpdate(userId);
    }


    @Override
    public void createUser(User user) {
        optimisticUserRepository.save(user);
    }


    @Override
    public Optional<User> findUserByUserId(String userId){
        return optimisticUserRepository.findUserByUserId(userId);
    }

    @Override
    public User saveUser(User user){
        return optimisticUserRepository.save(user);
    }
}
