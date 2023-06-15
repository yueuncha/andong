package com.tour.admin.member.service;

import com.tour.admin.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
    @Override
    public List<MemberVO> userView() {
        return null;
    }

    // private final MemberRepository memberRepository;

//    @Autowired
//    public MemberServiceImpl(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public List<MemberVO> userView(){
//        return memberRepository.selectUserList();
//    }

}
