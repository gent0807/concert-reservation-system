package io.dev.concertreservationsystem.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private UserGenderType gender;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public UserDTOResult convertToUserDTOResult() {
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
}
