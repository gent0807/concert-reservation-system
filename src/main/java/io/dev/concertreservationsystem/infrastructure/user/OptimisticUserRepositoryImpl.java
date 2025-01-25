package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("optimistic-lock")
public class OptimisticUserRepositoryImpl implements UserRepository {

    private final OptimisticUserJPARepository optimisticUserJPARepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return optimisticUserJPARepository.findUserByUserIdForUpdate(userId);
    }

    @Override
    public void createUser(User user) {
        optimisticUserJPARepository.save(user);
    }


    @Override
    public Optional<User> findUserByUserId(String userId){
        return optimisticUserJPARepository.findUserByUserId(userId);
    }

    @Override
    public User saveUser(User user){
        return optimisticUserJPARepository.save(user);
    }
}
