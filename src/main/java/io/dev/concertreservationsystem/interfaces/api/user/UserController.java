package io.dev.concertreservationsystem.interfaces.api.user;

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
public class UserController {

    @GetMapping
    @Operation(summary = "임시 로그인", description = "요청에 담긴 UUID를 이용하여 임시 로그인합니다.")
<<<<<<< Updated upstream:src/main/java/io/dev/concertreservationsystem/interfaces/api/member/MemberController.java
    public ResponseEntity<MemberResponseDTO> getMemberById(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new MemberResponseDTO(id, null, null, null, null));
=======
    public ResponseEntity<UserResponseDTO> getMemberById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(id, null, null, null, null));
>>>>>>> Stashed changes:src/main/java/io/dev/concertreservationsystem/interfaces/api/user/UserController.java
    }

    @PostMapping("/new")
    @Operation(summary = "임시 회원가입", description = "UUID를 랜덤 생성하여 임시 회원가입합니다.")
    public ResponseEntity<UserResponseDTO> insertMember(@RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO userResponseDTO = new UserResponseDTO(UUID.randomUUID().toString()
                                                            , userRequestDTO.memberName(), userRequestDTO.age(),
                                                                userRequestDTO.gender(), userRequestDTO.balance());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }
}
