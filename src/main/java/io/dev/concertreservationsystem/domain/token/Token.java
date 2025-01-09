package io.dev.concertreservationsystem.domain.token;

import io.dev.concertreservationsystem.interfaces.api.common.exception.error.ErrorCode;
import io.dev.concertreservationsystem.interfaces.api.common.exception.error.TokenInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Slf4j
public class Token {

    @Id
    private Long tokenId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "token_status", nullable = false)
    private TokenStatusType tokenStatus;

    @Column(name = "expires_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    public TokenDTOResult convertToTokenDTOResult() {
        return TokenDTOResult.builder()
                        .tokenId(this.tokenId)
                        .userId(this.userId)
                        .tokenStatus(this.tokenStatus)
                        .build();
    }

    public void checkStatus() {

        if(this.tokenStatus == TokenStatusType.ACTIVE){
            if(this.expiresAt.isBefore(LocalDateTime.now())){
                log.debug("expired token");
                throw new TokenInvalidException(ErrorCode.TOKEN_STATUS_EXPIRED);
            }
        }

        if (this.tokenStatus == TokenStatusType.INACTIVE){
            log.debug("inactive token");
            throw new TokenInvalidException(ErrorCode.TOKEN_STATUS_INACTIVE);
        }
    }
}
