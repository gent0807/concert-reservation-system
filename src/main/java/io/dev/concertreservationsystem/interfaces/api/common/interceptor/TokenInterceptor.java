package io.dev.concertreservationsystem.interfaces.api.common.interceptor;

import io.dev.concertreservationsystem.application.token.TokenAdminFacade;
import io.dev.concertreservationsystem.domain.token.TokenStatusType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenAdminFacade tokenAdminFacade;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        // Authorization 헤더에서 값 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        TokenStatusType tokenStatusType = tokenAdminFacade.checkTokenValidation();

        if(tokenStatusType == TokenStatusType.INACTIVE){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "아직 활성화되지 않은 토큰입니다.");

            return false;
        }

        if(tokenStatusType == TokenStatusType.EXPIRED){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "이미 만료된 토큰입니다.");

            return false;
        }


        return true;
    }
}
