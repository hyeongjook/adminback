package com.ict.edu3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userServiceImpl;  // UserServiceImpl을 UserDetailsService로 사용
    }
}
