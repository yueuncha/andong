package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreReadRepository {

    List<Map<String, Object>> categoryList();
    List<Map<String, Object>> storeSearch(Map<String, Object> param);
    List<Map<String, Object>> storeList();
    List<Map<String, Object>> categoryDetail();
    List<Map<String, Object>> storeDetail();


}
