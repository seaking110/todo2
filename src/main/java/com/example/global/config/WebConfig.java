package com.example.global.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter()); // 로그인 필터 등록
        filterRegistrationBean.setOrder(1);  // 우선순위 등록
        filterRegistrationBean.addUrlPatterns("/*"); // 들어오는 모든 URL 검증
        return filterRegistrationBean;
    }
}
