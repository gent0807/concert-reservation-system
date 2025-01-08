package io.dev.concertreservationsystem.interfaces.api.common.config;

import io.dev.concertreservationsystem.interfaces.api.common.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/concert-details/**", "/payments/**", "/reservations/**", "/seats/**")
                .excludePathPatterns("/users/**", "/tokens/new");
    }
}
