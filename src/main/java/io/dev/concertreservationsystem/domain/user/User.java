package io.dev.concertreservationsystem.domain.user;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.UserInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
    private Integer age;

    @Column(name = "gender", nullable = false)
    private UserGenderType gender;

    @Column(name = "point", nullable = false, columnDefinition = "INT SIGNED DEFAULT 0")
    private Integer point;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

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
        if(this.userId == null || this.userId.isBlank()){
            throw new UserInvalidException(ErrorCode.USER_ID_INVALID);
        }

        if(this.userName == null || this.userName.isBlank()){
            throw new UserInvalidException(ErrorCode.USER_NAME_INVALID);
        }

        if(this.age == null || this.age < 0){
            throw new UserInvalidException(ErrorCode.USER_AGE_INVALID);
        }

        if(this.gender == null || !Arrays.stream(UserGenderType.values()).toList().contains(this.gender)){
            throw new UserInvalidException(ErrorCode.USER_GENDER_TYPE_INVALID);
        }

        if(this.point == null || this.point < 0){
            throw new UserInvalidException(ErrorCode.USER_POINT_INVALID);
        }

        if(this.createdAt == null){
            throw new UserInvalidException(ErrorCode.USER_CREATED_AT_INVALID);
        }

        if(this.updatedAt == null){
            throw new UserInvalidException(ErrorCode.USER_UPDATED_AT_INVALID);
        }

    }

}
