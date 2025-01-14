package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.PaymentInvalidException;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "age", nullable = false)
    @Positive
    @Min(0)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private UserGenderType gender;

    @Column(name = "point", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    @Size(min = 0, max = 10_000_000)
    private Long point;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public User(String userId, String userName, Integer age, UserGenderType gender){
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
    }

    public static User createUser(String userId, String userName, Integer age, UserGenderType gender) {

        if(userId == null || userId.isBlank()){
            log.debug("userId is null or blank");
            throw new UserInvalidException(ErrorCode.USER_ID_INVALID);
        }

        if(userName == null || userName.isBlank()){
            log.debug("userName is null or blank");
            throw new UserInvalidException(ErrorCode.USER_NAME_INVALID);
        }

        if(age == null || age < 0){
            log.debug("age is null or less than 0");
            throw new UserInvalidException(ErrorCode.USER_AGE_INVALID);
        }

        if(gender == null && !Arrays.stream(UserGenderType.values()).toList().contains(gender)){
            log.debug("gender is null or not valid");
            throw new UserInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID);
        }

        return new User(userId, userName, age, gender);
    }


    public UserDTOResult convertToUserDTOResult() {

        checkValidation();

        return UserDTOResult.builder()
                            .userId(this.userId)
                            .userName(this.userName)
                            .age(this.age)
                            .gender(this.gender)
                            .point(this.point)
                            .createdAt(this.createdAt)
                            .updatedAt(this.updatedAt)
                            .deletedAt(this.deletedAt)
                            .build();

    }

    public void checkValidation() {
        checkUserIdValidation();

        checkUserNameValidation();

        checkUserAgeValidation();

        checkUserGenderValidation();

        checkUserPointValidation();

        checkUserCreatedAtValidation();

        checkUserUpdatedAtValidation();

    }

    public void checkUserUpdatedAtValidation() {
        if(this.updatedAt == null || this.updatedAt.isAfter(LocalDateTime.now())){
            log.debug("updatedAt is null");
            throw new UserInvalidException(ErrorCode.USER_UPDATED_AT_INVALID);
        }
    }

    public void checkUserCreatedAtValidation() {
        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            log.debug("createdAt is null");
            throw new UserInvalidException(ErrorCode.USER_CREATED_AT_INVALID);
        }
    }

    public void checkUserPointValidation() {
        if(this.point == null || this.point < 0){
            log.debug("point is null or less than 0");
            throw new UserInvalidException(ErrorCode.USER_POINT_INVALID);
        }
    }

    public void checkUserGenderValidation() {
        if(this.gender == null || !Arrays.stream(UserGenderType.values()).toList().contains(this.gender)){
            log.debug("gender is null or not valid");
            throw new UserInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID);
        }
    }

    public void checkUserAgeValidation() {
        if(this.age == null || this.age < 0){
            log.debug("age is null or less than 0");
            throw new UserInvalidException(ErrorCode.USER_AGE_INVALID);
        }
    }

    public void checkUserNameValidation() {
        if(this.userName == null || this.userName.isBlank()){
            log.debug("userName is null or blank");
            throw new UserInvalidException(ErrorCode.USER_NAME_INVALID);
        }
    }

    public void checkUserIdValidation() {
        if(this.userId == null || this.userId.isBlank()){
            log.debug("userId is null or blank");
            throw new UserInvalidException(ErrorCode.USER_ID_INVALID);
        }
    }

    public void chargePoint(long amount) {
        this.setPoint(this.point + amount);
    }

    public void usePoint(long price) {
        this.setPoint(this.point - price);
    }

    public void checkPrice(Integer totalPrice) {
        if(this.point < totalPrice){
            log.debug("point is under total price");
            throw new PaymentInvalidException(ErrorCode.PAYMENT_OVER_USER_POINT);
        }
    }


}
