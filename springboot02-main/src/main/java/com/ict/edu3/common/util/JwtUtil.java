package com.ict.edu3.common.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private String secretKey = "abcdASDF1234abcdQWER0987poiuPOIU";  // 비밀 키 (환경 변수나 안전한 방법으로 설정해야 합니다)
    private long expirationTime = 1000 * 60 * 60 * 10; // 만료 시간 (예: 10시간)

    // JWT 토큰 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)  // user_id를 subject로 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // JWT 토큰에서 사용자 ID를 추출하고 유효성 검증
    public String validateAndGetUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)  // 비밀 키를 설정
                .parseClaimsJws(token)  // 토큰을 파싱
                .getBody();

        // 토큰이 만료되지 않았는지 검증
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("JWT 토큰이 만료되었습니다.");
        }

        // 사용자 ID를 반환
        return claims.getSubject();  // JWT의 Subject는 user_id입니다.
    }

}
