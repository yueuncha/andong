package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMemberReadRepository {

    List<Map<String,Object>> selectUserList();

    Map<String, Object> selectUserOne(Map<String, Object> params);

    Map<String, Object> userLogin(String mb_email, String cryptKey);

    Integer userDupChk(String mb_param, String mb_value);

    Map<String, Object> userDataOne(String mb_param, int mb_idx);

    List<Map<String,Object>> localCategory(String mb_foreign);

    List<Map<String,Object>> localChoice(String set_1_code);

    Map<String , Object> sessionData(Map<String, Object> params);
    Map<String , Object> snsUserLogin(Map<String, Object> params);






}


