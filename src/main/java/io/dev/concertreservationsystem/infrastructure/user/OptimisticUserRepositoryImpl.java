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

    private final UserJPARepository optimisticUserJPARepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return optimisticUserJPARepository.findUserByUserIdForUpdateWithOptimisticLock(userId);
    }


    @Override
    public Optional<User> findUserByUserId(String userId){
        return optimisticUserJPARepository.findUserByUserId(userId);
    }

    @Override
    public User save(User user){
        return optimisticUserJPARepository.save(user);
    }
}
