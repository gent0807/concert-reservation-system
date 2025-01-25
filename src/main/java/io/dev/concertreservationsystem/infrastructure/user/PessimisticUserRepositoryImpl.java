package io.dev.concertreservationsystem.infrastructure.user;


import io.dev.concertreservationsystem.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("pessimistic-lock")
public class PessimisticUserRepositoryImpl implements UserRepository {

    private final PessimisticUserJPARepository pessimisticUserJPARepository;

    @Override
    public User findUserByUserIdWithLock(String userId) {
        return pessimisticUserJPARepository.findUserByUserIdForUpdate(userId);
    }


    @Override
    public void createUser(User user) {
        pessimisticUserJPARepository.save(user);
    }


    @Override
    public Optional<User> findUserByUserId(String userId){
        return pessimisticUserJPARepository.findUserByUserId(userId);
    }

    @Override
    public User saveUser(User user){
        return pessimisticUserJPARepository.save(user);
    }

}
