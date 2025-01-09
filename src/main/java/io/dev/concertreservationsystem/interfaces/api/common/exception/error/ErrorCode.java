package io.dev.concertreservationsystem.interfaces.api.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 유저 정보 관련 ErrorCode
    USER_ID_INVALID(HttpStatus.BAD_REQUEST, "USER_ID_INVALID", "유효하지 않은 유저 아이디입니다."),
    USER_NAME_INVALID(HttpStatus.BAD_REQUEST, "USER_NAME_INVALID", "유효하진 않은 유저 성명입니다."),
    USER_AGE_INVALID(HttpStatus.BAD_REQUEST, "USER_AGE_INVALID", "유효하지 않은 유저 나이입니다."),
    USER_GENDER_TYPE_INVALID(HttpStatus.BAD_REQUEST, "USER_GENDER_TYPE_INVALID", "유효하지 않은 성별입니다."),
    USER_POINT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_POINT_INVALID", "유효하지 않은 유저 포인트입니다."),
    USER_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_CREATED_AT_INVALID", "유효하지 않은 유저 가입 생성일입니다."),
    USER_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_UPDATED_AT_INVALID", "유효하진 않은 유저 정보 수정일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_NOT_FOUND", "존재하지 않는 유저입니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;
    private final String message;
}
