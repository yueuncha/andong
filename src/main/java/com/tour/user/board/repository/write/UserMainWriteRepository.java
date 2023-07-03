package com.tour.user.board.repository.write;

import com.tour.user.board.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMainWriteRepository{
    int userJoin(MemberVO vo);
}


