package com.ict.edu3.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.edu3.common.util.JwtUtil;
import com.ict.edu3.domain.user_info.service.UserService;
import com.ict.edu3.domain.user_info.vo.UserVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. HTTP 요청 헤더에서 Authorization 값을 가져옵니다.
        String authorizationHeader = request.getHeader("Authorization");

        // 2. Authorization 헤더가 없거나 "Bearer"로 시작하지 않으면 필터를 계속 진행.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer " 뒤의 JWT 토큰만 추출.
        String jwtToken = authorizationHeader.substring(7);

        try {
            // 4. JWT 토큰을 검증하고 user_id 추출.
            String userId = jwtUtil.validateAndGetUserIdFromToken(jwtToken);

            // 5. 해당 userId로 사용자 정보를 로드.
            UserVO user = userService.getusersById(userId);
            if (user != null) {
                // 6. 사용자가 존재하면 SecurityContext에 사용자 인증 정보 세팅.
                // (UserVO를 User로 변환하는 작업)
                org.springframework.security.core.userdetails.User userDetails = new User(
                        user.getUser_id(),  // 사용자 ID
                        user.getUser_pw(),  // 사용자 비밀번호
                        true,  // 활성화 여부
                        true,  // 계정 만료 여부
                        true,  // 자격 증명 만료 여부
                        true,  // 계정 잠금 여부
                        null  // Authorities (권한)은 추가하지 않음
                );

                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // 7. 인증 정보를 SecurityContext에 저장.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("JWT 인증 오류", e);
        }

        // 8. 필터 체인 다음 필터로 이동.
        filterChain.doFilter(request, response);
    }
}
