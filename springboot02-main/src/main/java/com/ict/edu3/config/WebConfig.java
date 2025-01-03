package com.ict.edu3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 /api/** 경로에 대해 CORS 허용
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")  // React 앱 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용되는 메서드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 쿠키와 인증 정보 허용
    }
}
