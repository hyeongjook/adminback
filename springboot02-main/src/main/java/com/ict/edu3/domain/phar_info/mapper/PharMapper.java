package com.ict.edu3.domain.phar_info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.edu3.domain.phar_info.vo.pharVO;

@Mapper
public interface PharMapper {

    List<pharVO> pharinfoList();

    // 수정하기
    int pharinfoUpdate(pharVO pvo);

    // 삭제하기
    int pharinfoDelete(String phar_idx);

    
    pharVO getpharsById(String phar_idx);

    // 생성하기
    int pharinfoWrite(pharVO pvo);

}
