package com.ict.edu3.domain.phar_info.service;

import java.util.List;

import com.ict.edu3.domain.phar_info.vo.pharVO;

public interface PharService {

    // 조회 불러오기

    List<pharVO> pharinfoList();

    // 수정하기
    int pharinfoUpdate(pharVO pvo);

    // 삭제하기
    int pharinfoDelete(String pharidx);

    // 생성하기
    int pharinfoWrite(pharVO pvo);

    // 상세보기
    pharVO getpharsById(String phar_idx);

}