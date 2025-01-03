package io.dev.concertreservationsystem.interfaces.api.member;

import io.dev.concertreservationsystem.domain.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member API", description = "MemberController")
public class MemberController {

    @GetMapping
    @Operation(summary = "임시 로그인", description = "요청에 담긴 UUID를 이용하여 임시 로그인합니다.")
    public ResponseEntity<MemberResponseDTO> getMemberById(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new MemberResponseDTO(id, null, null, null, null));
    }

    @PostMapping("/new")
    @Operation(summary = "임시 회원가입", description = "UUID를 랜덤 생성하여 임시 회원가입합니다.")
    public ResponseEntity<MemberResponseDTO> insertMember(@RequestBody MemberRequestDTO memberRequestDTO) {

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(UUID.randomUUID().toString()
                                                            , memberRequestDTO.memberName(), memberRequestDTO.age(),
                                                                memberRequestDTO.gender(), memberRequestDTO.balance());

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponseDTO);
    }
}
