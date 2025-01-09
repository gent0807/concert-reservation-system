package io.dev.concertreservationsystem.infrastructure.user;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Override
    public void createUser(User user) {
        userJPARepository.save(user);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return userJPARepository.findUserByUserId(userId);

    }

    @Override
    public void updateUser(User user){
        userJPARepository.save(user);
    }

}

