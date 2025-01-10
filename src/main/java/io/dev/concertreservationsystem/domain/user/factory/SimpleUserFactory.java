package io.dev.concertreservationsystem.domain.user.factory;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import org.springframework.stereotype.Component;

@Component
public class SimpleUserFactory extends UserFactory{

    @Override
    public User createUser(String userId, String userName, Integer age, UserGenderType userGenderType ){
        return User.builder()
                .userId(userId)
                .userName(userName)
                .age(age)
                .gender(userGenderType)
                .build();
    }

}
