package io.dev.concertreservationsystem.interfaces.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        long startTime = System.currentTimeMillis();

        String uuid = UUID.randomUUID().toString();

        // 요청 정보 로깅
        loggingRequest(httpRequest, "REQUEST-" + uuid);

        // 다음 필터(혹은 DispatcherServlet)로 진행
        chain.doFilter(httpRequest, response);

        // 응답 정보 로깅
        loggingResult(httpResponse, "RESPONSE-" + uuid, startTime);
    }


    private void loggingRequest(HttpServletRequest request, String uuid) {
        String queryString = request.getQueryString();
        log.info("UUID - [{}] | <<--------------------------------------------------------------------------",uuid);
        log.info("UUID - [{}] | START TIME : {}",uuid, LocalDateTime.now());
        log.info("UUID - [{}] | Request : {} uri=[{}] content-type=[{}]"
                , uuid
                , request.getMethod()
                , queryString == null ? request.getRequestURI() : request.getRequestURI() + "?" + queryString
                , request.getContentType());
    }

    private void loggingResult(HttpServletResponse httpResponse, String uuid, long startTime) {
        log.info("UUID - [{}] | Response : {} ", uuid, httpResponse.getStatus());
        log.info("UUID - [{}] | Request processed in {} ms", uuid, System.currentTimeMillis() - startTime);
        log.info("UUID - [{}] | END TIME : {}",uuid, LocalDateTime.now());
        log.info("UUID - [{}] | -------------------------------------------------------------------------->>",uuid);
    }


    @Override
    public void destroy() {
        // 필터 종료 로직 (필요 시)
    }
}
