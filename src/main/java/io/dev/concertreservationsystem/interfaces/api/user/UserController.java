package io.dev.concertreservationsystem.interfaces.api.user;

import io.dev.concertreservationsystem.application.user.UserFacadeDTOResult;
import io.dev.concertreservationsystem.application.user.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "유저 정보 관련 API", description = "유저 정보 관련 api입니다.")
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/new")
    @Operation(summary = "임시 회원가입", description = "UUID를 랜덤 생성하여 임시 회원가입하고, 헤더에 userId(UUID)를 담아보내줍니다.).")
    public ResponseEntity<Void> insertUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        // 접속 요청 발생하는 경우, UUID 랜덤 발급하고 유저 테이블에 저장하고, 새로 생성된 userId(UUID)를 담아 보내주는
        // 현재 참조된 UserAdminFacade 타입 객체의 insertUser 메소드 호출
        UserFacadeDTOResult userFacadeDTOResult = userFacade.insertUser(userRequestDTO.convertToUserFacadeDTOParam());

        HttpHeaders headers = new HttpHeaders();

        headers.set("X-Custom-UserId", userFacadeDTOResult.userId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();

    }

    @GetMapping(value = "/condition")
    @Operation(summary = "유저 상세 정보 조회", description = "요청 헤더에 담긴 userId(UUID)룰 이용하여 회원의 상세 정보를 조회합니다.")
    public ResponseEntity<UserResponseDTO> findUserByUserId(@RequestHeader("X-Custom-UserId") String userId) {

        /*
           // userId를 이용한 UserAdminDTOParam 객체 생성
           UserAdminDTOParam userAdminDTOParam = UserAdminDTOParam.builder()
                                                            .userId(Long.parseLong(userId))
                                                            .build();


            // 요청 헤더에 "X-Custom-UserId" 키 값으로 담긴 메타 데이터(사용자 아이디, UUID)를 이용해 유저 상세 정보를 조회하는,
            // 현재 참조된 UserAdminFacade 타입 객체의 findUser 메소드 호출

            UserAdminDTOResult userAdminDTOResult = userAdminFacade.findUser(userAdminDTOParam);

            UserResponseDTO userResponseDTO =  userAdminDTOResult.convertToUserResponseDTO();

            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
          */

        UserResponseDTO userResponseDTO = new UserResponseDTO(UUID.randomUUID().toString()
                , null, null,
                null,null);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @PatchMapping(value = "/condition")
    @Operation(summary = "유저 상세 정보 수정", description = "요청 헤더에 담긴 userId(UUID)와 요청 바디에 담긴 수정 정보를 이용하여 회원의 상세 정보를 수정합니다.")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestHeader("X-Custom-UserId") String userId, @RequestBody UserRequestDTO userRequestDTO) {
/*

        // userId와 userRequestDTO를 이용한 UserAdminDTOParam 생성
        UserAdminDTOParam userAdminDTOParam  = UserAdminDTOParam.builder()
                                                        .userId(Long.parseLong(userId))
                                                        .userName(userRequestDTO.userName())
                                                        .age(userRequestDTO.age())
                                                        .gender(userRequestDTO.gender())
                                                        .build();

        // userAdminDTOParam에 담긴 정보를 이용해, 유저의 상세 정보를 수정하는,
        // 현재 참조된 UserAdminFacade 타입 객체의 updateUser 메소드 호출
        UserAdminDTOResult userAdminDTOResult = userAdminFacade.updateUser(userAdminDTOParam);

        UserResponseDTO userResponseDTO = userAdminDTOResult.convertToUserResponseDTO();

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
*/

        UserResponseDTO userResponseDTO = new UserResponseDTO(UUID.randomUUID().toString()
                , null, null,
                null,null);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }
}
