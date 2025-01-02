package com.ict.edu3.domain.user_info.mapper;

import com.ict.edu3.domain.user_info.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserVO> userinfoList();

    UserVO getusersById(String user_idx);

    int userinfoUpdate(UserVO userVO);

    int userinfoDelete(String user_idx);

    int userinfoWrite(UserVO userVO);
}
