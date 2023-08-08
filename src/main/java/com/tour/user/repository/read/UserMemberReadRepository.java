package com.tour.user.repository.read;

import com.tour.user.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMemberReadRepository {

    List<MemberVO> selectUserList();

    MemberVO selectUserOne(int mb_idx);

    MemberVO userLogin(String mb_id);

    int userDupChk(String mb_param, String mb_value);

    Map<String, Object> userDataOne(String mb_param, int mb_idx);

}


