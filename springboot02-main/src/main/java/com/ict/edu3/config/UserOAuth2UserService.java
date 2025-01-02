package com.ict.edu3.config;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2User를 기본적으로 로드합니다.
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();  // OAuth2User의 속성 (예: name, email)

        // 필요한 추가 로직 처리 (예: DB 저장, 추가 필드 처리 등)
        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority("ROLE_USER", attributes)),
                attributes,
                "name");  // OAuth2User 반환 (name 속성을 기준으로 사용자를 식별)
    }
}
