package com.ict.edu3.domain.user_info.service;

import com.ict.edu3.domain.user_info.vo.UserVO;

import java.util.List;

public interface UserService {

    List<UserVO> userinfoList();

    UserVO getusersById(String user_idx);

    int userinfoUpdate(UserVO userVO);

    int userinfoDelete(String user_idx);

    int userinfoWrite(UserVO userVO);
}
