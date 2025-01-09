package io.dev.concertreservationsystem.domain.user.factory;

import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public abstract class UserFactory {

    public final User orderUser(String userId, String userName, Integer age, UserGenderType userGenderType) {

        checkUserValidationOnBuild(userId, userName, age, userGenderType);

        User user = createUser(userId, userName, age, userGenderType);

        return user;
    }

    public void checkUserValidationOnBuild(String userId, String userName, Integer age, UserGenderType userGenderType){

        checkUserIdValidation(userId);

        checkUserNameValidation(userName);

        checkAgeValidation(age);

        checkUserGenderTypeValidation(userGenderType);

    }

    public void checkUserIdValidation(String userId) {
        if(userId == null || userId.isBlank()){
            throw new UserInvalidException(ErrorCode.USER_ID_INVALID);
        }
    }

    public void checkUserNameValidation(String userName){
        if(userName == null || userName.isBlank()){
            throw new UserInvalidException(ErrorCode.USER_NAME_INVALID);
        }
    }

    public void checkAgeValidation(Integer age){
        if(age == null || age < 0){
            throw new UserInvalidException(ErrorCode.USER_AGE_INVALID);
        }
    }

    public void checkUserGenderTypeValidation(UserGenderType userGenderType){
        if(userGenderType == null || !Arrays.stream(UserGenderType.values()).toList().contains(userGenderType)){
            throw new UserInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID);
        }
    }



   protected abstract User createUser(String userId, String userName, Integer age, UserGenderType userGenderType);

}
