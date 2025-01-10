package io.dev.concertreservationsystem.domain.token.factory;

import io.dev.concertreservationsystem.domain.token.Token;
import io.dev.concertreservationsystem.domain.user.User;
import io.dev.concertreservationsystem.domain.user.UserGenderType;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;

import java.util.Arrays;

public abstract class TokenFactory {
    public final Token orderToken(String userId) {

        checkTokenValidationOnBuild(userId);

        Token token = createToken(userId);

        return token;
    }

    public void checkTokenValidationOnBuild(String userId){

        checkUserIdValidation(userId);

    }

    public void checkUserIdValidation(String userId) {
        if(userId == null || userId.isBlank()){
            throw new TokenInvalidException(ErrorCode.USER_ID_INVALID);
        }
    }


    protected abstract Token createToken(String userId);

}
