package io.dev.concertreservationsystem.interfaces.common.exception.error;

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
    POINT_HISTORY_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_UPDATED_AT_INVALID", "유효하지 않은 포인트 충전 차감 내역 수정일입니다."),
    POINT_HISTORY_SAVE_FAILED(HttpStatus.NOT_ACCEPTABLE, "POINT_HISTORY_SAVE_FAILED", "포인트 충전 차감 내역 저장에 실패했습니다."),

    // 콘서트 실제 공연 관련 ErrorCode
    CONCERT_BASIC_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_BASIC_ID_INVALID", "유효하지 않은 콘서트 기본 정보 아이디입니다."),
    RESERVABLE_CONCERT_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVABLE_CONCERT_DETAIL_NOT_FOUND", "해당 콘서트의 예약 가능한 실제 공연이 없습니다."),
    CONCERT_DETAIL_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_ID_INVALID", "유효하지 않은 콘서트 실제 공연 정보 아이디입니다."),
    CONCERT_DETAIL_STATUS_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_STATUS_INVALID", "유효하지 않은 콘서트 실제 공연 예약 상태 타입입니다."),
    CONCERT_DETAIL_START_TIME_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_START_TIME_INVALID", "유효하지 않은 콘서트 실제 공연 시작 시각입니다."),
    CONCERT_DETAIL_END_TIME_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_END_TIME_INVALID", "유효하지 않은 콘서트 실제 공연 종료 시각입니다."),
    CONCERT_DETAIL_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_CREATED_AT_INVALID", "유효하지 않은 콘서트 실제 공연 생성일입니다."),
    CONCERT_DETAIL_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "CONCERT_DETAIL_UPDATED_AT_INVALID", "유효하지 않은 콘서트 실제 공연 수정일입니다."),

    // 좌석 관련 ErrorCode
    RESERVABLE_SEAT_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "RESERVABLE_SEAT_NOT_FOUND", "해당 공연의 예약 가능한 좌석이 없습니다."),
    SEAT_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_ID_INVALID", "유효하지 않은 좌석 아이디입니다."),
    SEAT_STATUS_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_STATUS_INVALID", "유효하지 않은 좌석 상태 타입입니다."),
    SEAT_NUMBER_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_NUMBER_INVALID", "유효하지 않은 좌석 번호입니다."),
    SEAT_PRICE_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_PRICE_INVALID", "유효하지 않은 좌석 가격입니다."),
    SEAT_EXPIRED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_EXPIRED_AT_INVALID", "유효하지 않은 좌석 점유 만료 시간입니다,"),
    SEAT_CREATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_CREATED_AT_INVALID", "유효하지 않은 좌석 생성일입니다."),
    SEAT_UPDATED_AT_INVALID(HttpStatus.NOT_ACCEPTABLE, "SEAT_UPDATED_AT_INVALID", "유효하지 않은 좌석 수정일입니다."),
    SEAT_NOT_FOUND_BY_SEAT_ID(HttpStatus.NOT_FOUND, "SEATS_NOT_FOUND_BY_USER_ID_PAYMENT_ID", "좌석 아이디로 좌석 정보가 존재하지 않습니다."),

    // 예약 관련 ErrorCode
    RESERVATION_NOT_RESERVABLE_SEAT(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_NOT_RESERVABLE_SEAT", "예약 불가한 좌석을 포함하고 있습니다."),
    RESERVATION_NOT_RESERVABLE_CONCERT_DETAIL(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_NOT_RESERVABLE_CONCERT_DETAIL", "예약 불가한 콘서트 실제 공연을 포함하고 있습니다."),
    RESERVATION_USER_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_USER_ID_INVALID", "예약 정보에 유효하지 않은 유저 아이디가 있습니다."),
    RESERVATION_SEAT_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_SEAT_ID_INVALID", "예약 정보에 유효하지 않은 좌석 아이디가 있습니다."),
    RESERVATION_PAYMENT_ID_INVALID(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_PAYMENT_ID_INVALID", "예약 정보에 유효하지 않은 결제 아이디가 있습니다."),
    RESERVATION_SAVE_FAILED(HttpStatus.NOT_ACCEPTABLE,"RESERVATION_SAVE_FAILED", "예약 정보 저장에 실패했습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVATION_NOT_FOUND", "존재하지 않는 좌석 예약 정보입니다,"),
    RESERVATION_STATUS_INVALID(HttpStatus.NOT_ACCEPTABLE, "RESERVATION_STATUS_INVALID", "유효하지 않은 좌석 예약 상태입니다."),

    // 결제 관련 ErrorCode
    PAYMENT_SAVE_FAILED(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_SAVE_FAILED", "결제 정보 저장에 실패했습니다."),
    PAYMENT_TOTAL_PRICE_INVALID(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_TOTAL_PRICE_INVALID", "유효하지 않은 결제 가격입니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_NOT_FOUND", "존재하지 않는 결제 정보입니다."),
    PAYMENT_OVER_USER_POINT(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_OVER_USER_POINT", "잔고 부족입니다. 유저 포인트 충전이 필요합니다."),
    PAYMENT_STATUS_INVALID(HttpStatus.NOT_ACCEPTABLE, "PAYMENT_STATUS_INVALID", "유효하지 않은 결제 정보 상태입니다.");




    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;
    private final String message;
}
