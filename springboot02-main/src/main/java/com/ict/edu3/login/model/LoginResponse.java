package com.ict.edu3.login.model;

public class LoginResponse {
    private String message;
    private boolean isAuthenticated;
    private String role;  // 사용자의 역할 (예: 관리자, 일반 사용자 등)

    // 기본 생성자 추가
    public LoginResponse() {
        this.message = "";
        this.isAuthenticated = false;
        this.role = "";
    }

    // 생성자 1: message와 isAuthenticated만 초기화
    public LoginResponse(String message, boolean isAuthenticated) {
        this.message = message;
        this.isAuthenticated = isAuthenticated;
    }

    // 생성자 2: message, isAuthenticated, role을 모두 초기화
    public LoginResponse(String message, boolean isAuthenticated, String role) {
        this.message = message;
        this.isAuthenticated = isAuthenticated;
        this.role = role;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
