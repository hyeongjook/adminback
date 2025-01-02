package com.ict.edu3.domain.user_info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu3.domain.user_info.service.UserService;
import com.ict.edu3.domain.user_info.vo.UserVO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<List<UserVO>> getAllUsers() {
        List<UserVO> users = userService.userinfoList();
        return ResponseEntity.ok(users);  // 목록을 반환
    }

    // 사용자 상세 조회
    @GetMapping("/{user_idx}")
    public ResponseEntity<UserVO> getUserById(@PathVariable String user_idx) {
        UserVO user = userService.getusersById(user_idx);
        if (user != null) {
            return ResponseEntity.ok(user);  // 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build();  // 사용자 정보가 없으면 404 반환
        }
    }

    // 사용자 정보 수정
    @PutMapping("/{user_idx}")
    public ResponseEntity<UserVO> updateUser(@PathVariable int user_idx, @RequestBody UserVO userVO) {
        userVO.setUser_idx(user_idx);  // 클라이언트가 받은 user_idx로 수정할 사용자 설정
        int result = userService.userinfoUpdate(userVO);
        if (result > 0) {
            return ResponseEntity.ok(userVO);  // 수정된 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build();  // 수정 실패 시 404 반환
        }
    }

    // 사용자 정보 삭제
    @DeleteMapping("/{user_idx}")
    public ResponseEntity<Void> deleteUser(@PathVariable String user_idx) {
        int result = userService.userinfoDelete(user_idx);
        if (result > 0) {
            return ResponseEntity.noContent().build();  // 삭제 성공 시 204 반환
        } else {
            return ResponseEntity.notFound().build();  // 삭제 실패 시 404 반환
        }
    }

    // 새 사용자 추가
    @PostMapping
    public ResponseEntity<UserVO> addUser(@RequestBody UserVO userVO) {
        int result = userService.userinfoWrite(userVO);
        if (result > 0) {
            return ResponseEntity.status(201).body(userVO);  // 추가 성공 시 201 반환
        } else {
            return ResponseEntity.badRequest().build();  // 추가 실패 시 400 반환
        }
    }
}
