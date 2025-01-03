package com.ict.edu3.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.login.model.LoginRequest;
import com.ict.edu3.login.model.LoginResponse;
import com.ict.edu3.login.service.LoginService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")  // CORS 설정
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // 임시로 관리자 로그인 처리
        if ("admin".equals(loginRequest.getUsername()) && "adminpassword".equals(loginRequest.getPassword())) {
            // 관리자일 경우 로그인 성공
            LoginResponse response = new LoginResponse();
            response.setMessage("관리자 로그인 성공");
            response.setIsAuthenticated(true);
            response.setRole("admin");  // 관리자 역할 추가
            return response;
        } else {
            // 다른 사용자일 경우 서비스에서 처리
            return loginService.authenticate(loginRequest);
        }
    }
}
