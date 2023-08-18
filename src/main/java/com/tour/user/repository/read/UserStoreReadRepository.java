package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreReadRepository {

    List<Map<String, Object>> storeSearch(Map<String, Object> params);
    List<Map<String, Object>> categoryList(Map<String, Object> params);
    List<Map<String, Object>> categoryDetail(String str_category, String lang, String mb_idx);
    List<Map<String, Object>> storeDetail(String str_idx, String lang, String mb_idx);




}
