package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMemberReadRepository {

    List<Map<String,Object>> selectUserList();

    Map<String, Object> selectUserOne(int mb_idx);

    Map<String, Object> userLogin(String mb_id);

    Integer userDupChk(String mb_param, String mb_value);

    Map<String, Object> userDataOne(String mb_param, int mb_idx);

    List<Map<String,Object>> localCategory(String mb_foreign);

    List<Map<String,Object>> localChoice(String set_1_code);

}


