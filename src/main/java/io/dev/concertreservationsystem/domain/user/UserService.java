package io.dev.concertreservationsystem.domain.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTOResult createUser() {

        User user = User.builder()
                        .userId(UUID.randomUUID().toString())
                        .build();

        userRepository.createUser(user);

        return userRepository.findUserByUserId(user.getUserId())
                                        .orElseThrow(() -> new RuntimeException("User not found"))
                                                .convertToUserDTOResult();
    }
}
