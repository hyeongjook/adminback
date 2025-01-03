package com.ict.edu3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ict.edu3.domain.user_info.service.UserServiceImpl;
import com.ict.edu3.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserOAuth2UserService userOAuth2UserService;
    private final UserServiceImpl userServiceImpl;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserOAuth2UserService userOAuth2UserService, UserServiceImpl userServiceImpl) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userOAuth2UserService = userOAuth2UserService;
        this.userServiceImpl = userServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // OAuth2 로그인 설정
        http.oauth2Login()
            .userInfoEndpoint()
            .userService(userOAuth2UserService);

        // CORS 설정을 Security에 추가
        http.cors().configurationSource(corsConfigurationSource());

        // OPTIONS 요청을 허용 (프리플라이트 요청 처리)
        http.cors().and().headers().frameOptions().disable();

        return http.build();
    }

    // UserDetailsService를 위한 빈 설정
    @Bean
    public UserDetailsService userDetailsService() {
        return userServiceImpl;  // UserServiceImpl을 UserDetailsService로 사용
    }

    // CORS 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 오리진 (프론트엔드 주소)
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS"); // OPTIONS 메서드 추가
        config.addAllowedHeader("*");  // 모든 헤더 허용
        config.setAllowCredentials(true);  // 자격 증명(쿠키 등)을 허용

        // /api/** 경로에 대한 CORS 설정을 등록
        source.registerCorsConfiguration("/api/**", config);

        return source;
    }
}
