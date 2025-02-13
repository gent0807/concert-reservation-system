package io.dev.concertreservationsystem.common.interceptor;

import io.dev.concertreservationsystem.application.token.TokenFacadeDTOParam;
import io.dev.concertreservationsystem.application.token.TokenFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenFacade tokenFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Authorization 헤더에서 값 가져오기
        String tokenId = request.getHeader("Authorization");

        String userId = request.getHeader("X-Custom-UserId");

        // 토큰 상태 유효성 검사(내부에서 exception 처리)
        tokenFacade.checkTokenStatusValidation(TokenFacadeDTOParam.builder()
                .tokenId(tokenId)
                .userId(userId)
                .build());

        return true;


    }
}
