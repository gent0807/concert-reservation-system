package io.dev.concertreservationsystem.interfaces.api.token;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokens")
@Tag(name="Token API", description="TokenController")
public class TokenController {

    @PostMapping("/new")
    @Operation(summary = "회원 대기 토큰 등록", description = "회원 대기 토큰을 생성에 헤더에 담아보내줍니다.")
    public ResponseEntity<?> insertToken(@RequestBody TokenRequestDTO tokenRequestDTO) {

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(new Random().nextInt(), tokenRequestDTO.memberId(), null, null);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenRequestDTO.tokenId().toString());

        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }
}
