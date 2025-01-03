package com.ict.edu3.login.service;

import org.springframework.stereotype.Service;

import com.ict.edu3.login.model.LoginRequest;
import com.ict.edu3.login.model.LoginResponse;

@Service
public class LoginService {

    public LoginResponse authenticate(LoginRequest loginRequest) {
        // 로그인 시 "admin" 계정으로 로그인되도록 처리
        if ("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            return new LoginResponse("관리자로 로그인 성공", true, "admin");
        } else if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            return new LoginResponse("일반 사용자 로그인 성공", true, "user");
        }

        return new LoginResponse("로그인 실패", false, "");
    }
}
