package io.dev.concertreservationsystem.common.config.filter;

import io.dev.concertreservationsystem.common.filter.LoggingFilter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoggingFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");

        return bean;
    }

}
