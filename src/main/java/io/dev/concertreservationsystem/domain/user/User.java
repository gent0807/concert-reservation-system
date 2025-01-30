package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Slf4j
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Version
    private Integer version;

    @Column(name = "age", nullable = false)
    @Positive
    @Min(0)
    private int age;

    @Column(name = "point", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
    @Positive
    @Min(0)
    private long point;

    @Column(name = "gender", nullable = false)
    private UserGenderType gender;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    private static final long USER_INIT_POINT = 0L;

    public User(String userId, String userName, Integer age, UserGenderType gender){
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.point = USER_INIT_POINT;
        this.gender = gender;
    }

    public static User createUser(String userId, String userName, Integer age, UserGenderType gender) {

        if(userId == null || userId.isBlank()){
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "USER", "createUser");
        }

        if(userName == null || userName.isBlank()){
            throw new DomainModelParamInvalidException(ErrorCode.USER_NAME_INVALID, "USER", "createUser");
        }

        if(age == null || age < 0){
            throw new DomainModelParamInvalidException(ErrorCode.USER_AGE_INVALID, "USER", "createUser");
        }

        if(gender == null && !Arrays.stream(UserGenderType.values()).toList().contains(gender)){
            throw new DomainModelParamInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID, "USER", "createUser");
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
            throw new DomainModelParamInvalidException(ErrorCode.USER_UPDATED_AT_INVALID, "USER", "checkUserUpdatedAtValidation");
        }
    }

    public void checkUserCreatedAtValidation() {
        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            throw new DomainModelParamInvalidException(ErrorCode.USER_CREATED_AT_INVALID, "USER", "checkUserCreatedAtValidation");
        }
    }

    public void checkUserPointValidation() {
        if(this.point < 0){
            throw new DomainModelParamInvalidException(ErrorCode.USER_POINT_INVALID, "USER", "checkUserPointValidation");
        }
    }

    public void checkUserGenderValidation() {
        if(this.gender == null || !Arrays.stream(UserGenderType.values()).toList().contains(this.gender)){
            throw new DomainModelParamInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID, "USER", "checkUserGenderValidation");
        }
    }

    public void checkUserAgeValidation() {
        if( this.age < 0){
            throw new DomainModelParamInvalidException(ErrorCode.USER_AGE_INVALID, "USER", "checkUserAgeValidation");
        }
    }

    public void checkUserNameValidation() {
        if(this.userName == null || this.userName.isBlank()){
            throw new DomainModelParamInvalidException(ErrorCode.USER_NAME_INVALID, "USER", "checkUserNameValidation");
        }
    }

    public void checkUserIdValidation() {
        if(this.userId == null || this.userId.isBlank()){
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "USER", "checkUserIdValidation");
        }
    }

    public void chargePoint(long amount) {
        this.setPoint(this.point + amount);
    }

    public void usePoint(long price) {
        this.setPoint(this.point - price);
    }

    public void checkPrice(Long totalPrice) {
        if(this.point < totalPrice){
            throw new DomainModelParamInvalidException(ErrorCode.PAYMENT_OVER_USER_POINT, "USER", "checkPrice");
        }
    }


}
