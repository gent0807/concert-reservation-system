package io.dev.concertreservationsystem.application.user;

import io.dev.concertreservationsystem.interfaces.api.common.validation.interfaces.CreateUser;
import io.dev.concertreservationsystem.domain.user.UserDTOResult;
import io.dev.concertreservationsystem.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class UserFacade {
    private final UserService userService;

    // 1. 임시 회원가입. 새로운 회원을 등록 저장, Post
    @Validated(CreateUser.class)
    public UserFacadeDTOResult insertUser(@Valid UserFacadeDTOParam userFacadeDTOParam) {

             // 랜덤으로 UUID를 생성하여 새로운 유저를 등록하는, 현재 참조된 UserService 타입 객체의 createUser 메소드 호출
             UserDTOResult userDTOResult = userService.insertUser(userFacadeDTOParam.convertToUserDTOParam());

             // UserAdminDTOResult 타입 객체 반환
             return userDTOResult.convertToUserFacadeDTOResult();

    }

    // 2. 유저 상세 정보 조회, Get
    public void findUser(UserFacadeDTOParam userFacadeDTOParam){
        /*

            return userDTOResult.convertToUserAdminDTOResult();

        */
    }

    // 3. 유저 상세 정보 수정
    public void updateUser(UserFacadeDTOParam userFacadeDTOParam){
          /*

            // userDTOParam 타입 객체를 이용하여 유저의 상세 정보를 수정하는, 현재 참조된 UserService 타입 객체의 updatgeUser 메소드 호출
            UserDTOResult userDTOResult = userService.updateUser(userAdminDTOParam.convertToUserDTOParam(););

            // UserDTOResult 타입 객체를 UserAdminDTOResult 타입으로 변환하는 메소드 호출
            return userDTOResult.convertToUserAdminDTOResult();

        */
    }
}
