package com.tour.user.board.repository.read;

import com.tour.user.board.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMainReadRepository {

    List<MemberVO> selectUserList();

    MemberVO selectUserOne(int mb_idx);

    MemberVO userLogin(String mb_id);

    int userDupChk(String mb_param, String mb_value);

    MemberVO userDataOne(String mb_param, int mb_idx);

}


