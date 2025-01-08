package io.dev.concertreservationsystem.interfaces.api.token;


import io.dev.concertreservationsystem.application.token.TokenAdminDTOParam;
import io.dev.concertreservationsystem.application.token.TokenAdminDTOResult;
import io.dev.concertreservationsystem.application.token.TokenAdminFacade;
import io.dev.concertreservationsystem.interfaces.api.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokens")
@Tag(name="유저 토큰 발급 API(과제)", description="유저의 토큰을 발급하는 api입니다.")
public class TokenController {

    private final TokenAdminFacade tokenAdminFacade;

    @PostMapping
    @Operation(summary = "회원 대기열 토큰 발급/ 임시 로그인", description = "요청 헤더에 담긴 userId를 이용하여 회원 대기열 토큰을 생성해 토큰 아이디를 헤더에 담아보내줍니다.")
    public ResponseEntity<Void> publishToken(@RequestHeader("X-Custom-UserId") String userId) {
/*

        // userId를 이용하여 TokenAdminDTOParam 객체 생성
        TokenAdminDTOParam tokenAdminDTOParam = TokenAdminDTOParam.builder()
                                                    .userId(userId)
                                                    .build();

        // tokenAdminDTOParam의 userId를 이용하여 유저의 회원가입 여부를 조회하고, 유저의 대기열 토큰을 발급하는,
        // 현재 참조된  tokenAdminFacade의 publishToken 메소드 호출
        TokenAdminDTOResult tokenAdminDTOResult = tokenAdminFacade.publishToken(tokenAdminDTOParam);

        // tokenAdminDTOResult의 tokenId를 헤더에 담기
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenAdminDTOResult.tokenId().toString());

        // tokenAdminDTOResult의 userId(UUID)를 헤더에 담기
        headers.set("X-Custom-UserId", tokenAdminDTOResult.userId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();

*/

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
