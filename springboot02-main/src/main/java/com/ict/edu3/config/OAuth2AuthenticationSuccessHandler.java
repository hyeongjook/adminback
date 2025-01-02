package com.ict.edu3.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // OAuth2 인증 토큰을 OAuth2User로 캐스팅
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();

        // OAuth2User 속성에 접근
        Map<String, Object> attributes = oauth2User.getAttributes();
        String userName = (String) attributes.get("name"); // 사용자 이름 가져오기
        String email = (String) attributes.get("email");  // 이메일 가져오기

        // 로그로 인증 성공 정보 출력 (예시)
        System.out.println("OAuth2 Login Successful: " + userName + " (" + email + ")");
        
        // 여기서 추가적으로 사용자 정보를 DB에 저장하거나, 세션에 사용자 정보를 저장할 수 있음

        // 이후, 인증이 성공했을 때 리디렉션하거나, 추가 작업을 처리할 수 있음
        response.sendRedirect("/home");  // 인증 성공 후 홈 페이지로 리디렉션 (예시)
    }
}
