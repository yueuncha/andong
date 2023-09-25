package com.tour.user.repository.write;

import com.tour.user.vo.RequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface UserMemberWriteRepository {
    int userJoin(Map<String, Object> param);
    int fcmInsert(Map<String, Object> param);
    int passwordChange(Map<String, Object> param);
    int sessionChk(Map<String, Object> param);

    int updateEmailChk(Map<String, Object> params);
    int alarmCheck(Map<String, Object> params);
}


