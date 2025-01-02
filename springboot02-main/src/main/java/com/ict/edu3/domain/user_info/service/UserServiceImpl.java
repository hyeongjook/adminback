package com.ict.edu3.domain.user_info.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.user_info.mapper.UserMapper;
import com.ict.edu3.domain.user_info.vo.UserVO;

@Service
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // user_id로 사용자 정보를 조회
        UserVO userVO = userMapper.getusersById(username);

        if (userVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // UserDetails 객체를 생성하여 반환 (기본적으로 username과 password를 설정)
        return User.builder()
                .username(userVO.getUser_id())
                .password(userVO.getUser_pw())
                .roles("USER")  // 예시로 "USER" 역할 설정
                .build();
    }

    // 기존 UserService의 메서드들 (사용자 생성, 수정 등)
    @Override
    public List<UserVO> userinfoList() {
        return userMapper.userinfoList();
    }

    @Override
    public int userinfoUpdate(UserVO uvo) {
        return userMapper.userinfoUpdate(uvo);
    }

    @Override
    public int userinfoDelete(String user_idx) {
        return userMapper.userinfoDelete(user_idx);
    }

    @Override
    public int userinfoWrite(UserVO uvo) {
        return userMapper.userinfoWrite(uvo);
    }

    @Override
    public UserVO getusersById(String user_idx) {
        return userMapper.getusersById(user_idx);
    }
}
