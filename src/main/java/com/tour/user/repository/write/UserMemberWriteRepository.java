package com.tour.user.repository.write;

import com.tour.user.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMemberWriteRepository {
    int userJoin(MemberVO vo);
}


