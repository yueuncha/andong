package com.tour.user.repository.write;

import com.tour.user.vo.RequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface UserMemberWriteRepository {
    int userJoin(RequestVO vo);
    int passwordChange(Map<String, Object> param);

}


