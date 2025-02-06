package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile({"default", "redis"})
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return userJPARepository.findUserByUserIdForUpdateWithOptimisticLock(userId);

    }

    @Override
    public Optional<User> findUserByUserId(String userId){
        return userJPARepository.findUserByUserId(userId);
    }

    @Override
    public User save(User user){
        return userJPARepository.save(user);
    }

}

