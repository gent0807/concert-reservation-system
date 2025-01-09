package io.dev.concertreservationsystem.interfaces.api.common.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 유저 정보 관련 ErrorCode
    USER_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_ID_INVALID", "유효하지 않은 유저 아이디입니다."),
    USER_NAME_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_NAME_INVALID", "유효하진 않은 유저 성명입니다."),
    USER_AGE_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_AGE_INVALID", "유효하지 않은 유저 나이입니다."),
    USER_GENDER_TYPE_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_GENDER_TYPE_INVALID", "유효하지 않은 성별입니다."),
    USER_POINT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_POINT_INVALID", "유효하지 않은 유저 포인트입니다."),
    USER_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_CREATED_AT_INVALID", "유효하지 않은 유저 가입 생성일입니다."),
    USER_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "USER_UPDATED_AT_INVALID", "유효하진 않은 유저 정보 수정일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_NOT_FOUND", "존재하지 않는 유저입니다."),

    // 토큰 정보 관련 ErrorCode
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN_NOT_FOUND", "존재하지 않는 토큰입니다."),
    TOKEN_STATUS_EXPIRED(HttpStatus.NOT_ACCEPTABLE, "TOKEN_STATUS_EXPIRED", "이미 만료된 토큰입니다."),
    TOKEN_STATUS_INACTIVE(HttpStatus.NOT_ACCEPTABLE, "TOKEN_STATUS_INACTIVE", "아직 활성화되지 않은 토큰입니다."),
    TOKEN_SAVE_FAILED(HttpStatus.EXPECTATION_FAILED, "TOKEN_SAVE_FAILED", "토큰 저장에 실패했습니다."),
    TOKEN_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "TOKEN_ID_INVALID", "유효하지 않은 토큰 아이디입니다."),
    TOKEN_STATUS_INVALID(HttpStatus.NOT_ACCEPTABLE, "TOKEN_STATUS_INVALID", "유효하지 않은 토큰 상태 타입입니다."),
    TOKEN_EXPIRED_AT_NONE(HttpStatus.NOT_ACCEPTABLE, "TOKEN_EXPIRED_AT_NONE", "활성화된 토큰의 만료일 존재하지 않습니다."),
    TOKEN_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "TOKEN_CREATED_AT_INVALID", "유효하지 않은 토큰 생성일입니다."),
    TOKEN_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "TOKEN_UPDATED_AT_INVALID", "유효하지 않은 토큰 수정일입니다." ),

    // 포인트 충전 차감 내역 관련 ErrorCode
    POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_POINT_TRANSACTION_TYPE_INVALID", "유효하지 않은 포인트 충전/차감 타입입니다."),
    POINT_HISTORY_AMOUNT_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_AMOUNT_INVALID" , "유효하지 않은 포인트 충전 차감량입니다."),
    POINT_HISTORY_RESULT_POINT_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_RESULT_POINT_INVALID", "유효하지 않은 유저의 총 포인트 잔고입니다."),
    POINT_HISTORY_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_ID_INVALID", "유효하지 않은 포인트 충전 차감 내역 아이디입니다."),
    POINT_HISTORY_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_CREATED_AT_INVALID", "유효하진 않은 포인트 충전 차감 내역 생성일입니다."),
    POINT_HISTORY_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_UPDATED_AT_INVALID", "유효하지 않은 포인트 충전 차감 내역 수정일입니다.");


    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;
    private final String message;
}
