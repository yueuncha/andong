package com.tour.user.board.repository;

import com.tour.user.board.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMainRepository {

    List<MemberVO> selectUserList();

    MemberVO selectUserOne(int mb_idx);

    MemberVO userLogin(String mb_id);

    Integer userDupChk(String mb_param, String mb_value);

    MemberVO userDataOne(String mb_param, int mb_idx);

    Integer userJoin(MemberVO vo);

}
