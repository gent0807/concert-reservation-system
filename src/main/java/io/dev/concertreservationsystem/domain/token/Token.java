package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.common.exception.error.DomainModelParamInvalidException;
import io.dev.concertreservationsystem.common.exception.error.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Slf4j
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    @Positive
    @Min(0)
    private Long tokenId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "token_status", nullable = false, columnDefinition = "ENUM('INACTIVE')")
    private TokenStatusType tokenStatus;

    @Column(name = "expires_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiresAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public Token(String userId, TokenStatusType tokenStatus){
        this.userId = userId;
        this.tokenStatus = tokenStatus;
    }

    public static Token createToken(String userId, TokenStatusType tokenStatus){

        if(userId == null || userId.isBlank()){
            log.error("userId is null or blank");
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "TOKEN", "createToken");
        }

        return new Token(userId, tokenStatus);
    }

    public TokenDTOResult convertToTokenDTOResult() {

        checkValidation();

        return TokenDTOResult.builder()
                        .tokenId(this.tokenId)
                        .userId(this.userId)
                        .tokenStatus(this.tokenStatus)
                        .build();
    }

    public void checkValidation() {
        if(this.tokenId == null || this.tokenId < 0){
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_ID_INVALID, "TOKEN", "checkValidation");
        }

        if(this.userId == null || this.userId.isBlank()){
            throw new DomainModelParamInvalidException(ErrorCode.USER_ID_INVALID, "TOKEN", "checkValidation");
        }

        if(this.tokenStatus == null || !Arrays.stream(TokenStatusType.values()).toList().contains(this.tokenStatus)){
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_STATUS_INVALID, "TOKEN", "checkValidation");
        }

        if(this.tokenStatus == TokenStatusType.ACTIVE && this.expiresAt == null){
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_EXPIRED_AT_NONE, "TOKEN", "checkValidation");
        }

        if(this.createdAt == null || this.createdAt.isAfter(LocalDateTime.now())){
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_CREATED_AT_INVALID, "TOKEN", "checkValidation");
        }

        if(this.updatedAt == null || this.updatedAt.isAfter(LocalDateTime.now())){
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_UPDATED_AT_INVALID, "TOKEN", "checkValidation");
        }
    }

    public void checkStatus() {

        if(this.tokenStatus == TokenStatusType.ACTIVE){
            if(this.expiresAt.isBefore(LocalDateTime.now())){
                log.error("expired token");
                throw new DomainModelParamInvalidException(ErrorCode.TOKEN_STATUS_EXPIRED, "TOKEN", "checkStatus");
            }
        }

        if (this.tokenStatus == TokenStatusType.INACTIVE){
            log.error("inactive token");
            throw new DomainModelParamInvalidException(ErrorCode.TOKEN_STATUS_INACTIVE, "TOKEN", "checkStatus");
        }
    }

    public void activeToken() {
        this.tokenStatus = TokenStatusType.ACTIVE;
        this.expiresAt = LocalDateTime.now().plusMinutes(6);
        this.updatedAt = LocalDateTime.now();
    }

    public void expireToken() {
        this.tokenStatus = TokenStatusType.Expired;
        this.expiresAt = null;
        this.updatedAt = LocalDateTime.now();
    }
}
