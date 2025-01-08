package io.dev.concertreservationsystem.application.user;

import io.dev.concertreservationsystem.domain.user.UserDTOParam;
import io.dev.concertreservationsystem.domain.user.UserDTOResult;
import io.dev.concertreservationsystem.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAdminFacade {
    private final UserService userService;

    // 1. 임시 회원가입. 새로운 회원을 등록 저장, Post
    public void insertUser() {
        /*
             // 랜덤으로 UUID를 생성하여 새로운 유저를 등록하는, 현재 참조된 UserService 타입 객체의 saveUser 메소드 호출
             UserDTOResult userDTOResult = userService.saveUser();

             // UserDTOResult 타입 객체를 UserAdminDTOResult 타입으로 변환하는 메소드 호출
             UserAdminDTOResult userAdminDTOResult = userDTOResult.convertToUserAdminDTOResult();

             // UserAdminDTOResult 타입 객체 반환
             return userAdminDTOResult;
         */
    }

    // 2. 유저 상세 정보 조회, Get
    public void findUser(UserAdminDTOParam userAdminDTOParam){
        /*

            // UserAdminDTOParam 타입 객체를 UserDTOParam 타입 객체로 변환
            UserDTOParam userDTOParam = userAdminDTOParam.convertToUserDTOParam();

            // userDTOParam 타입 객체를 이용하여 유저를 찾는, 현재 참조된 UserService 타입 객체의 findUser 메소드 호출
            UserDTOResult userDTOResult = userService.findUser(userDTOParam);

            // UserDTOResult 타입 객체를 UserAdminDTOResult 타입으로 변환하는 메소드 호출
            UserAdminDTOResult userAdminDTOResult = userDTOResult.convertToUserAdminDTOResult();

            return userAdminDTOResult;

        */
    }

    // 3. 유저 상세 정보 수정
    public void updateUser(UserAdminDTOParam userAdminDTOParam){
          /*

            // UserAdminDTOParam 타입 객체를 UserDTOParam 타입 객체로 변환
            UserDTOParam userDTOParam = userAdminDTOParam.convertToUserDTOParam();

            // userDTOParam 타입 객체를 이용하여 유저의 상세 정보를 수정하는, 현재 참조된 UserService 타입 객체의 updatgeUser 메소드 호출
            UserDTOResult userDTOResult = userService.updateUser(userDTOParam);

            // UserDTOResult 타입 객체를 UserAdminDTOResult 타입으로 변환하는 메소드 호출
            UserAdminDTOResult userAdminDTOResult = userDTOResult.convertToUserAdminDTOResult();

            return userAdminDTOResult;

        */
    }
}
