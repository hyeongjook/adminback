package com.ict.edu3.domain.phar_info.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu3.domain.phar_info.mapper.PharMapper;
import com.ict.edu3.domain.phar_info.vo.pharVO;

@Service
public class PharServiceImpl implements PharService {

    @Autowired
    private PharMapper pharMapper;

    @Override
    public pharVO getpharsById(String phar_idx) {

        return pharMapper.getpharsById(phar_idx);
    }

    @Override
    public int pharinfoDelete(String pharidx) {

        return pharMapper.pharinfoDelete(pharidx);
    }


    @Override
    public List<pharVO> pharinfoList() {
        List<pharVO> list = pharMapper.pharinfoList();
        if (list == null) {
            list = new ArrayList<>(); // null일 경우 빈 리스트로 초기화
            System.out.println("데이터가 없습니다.");
        }
        return list;
    }

    @Override
    public int pharinfoUpdate(pharVO pvo) {

        return pharMapper.pharinfoUpdate(pvo);
    }

    @Override
    public int pharinfoWrite(pharVO pvo) {

        return pharMapper.pharinfoWrite(pvo); // Mapper 호출
    }

}
