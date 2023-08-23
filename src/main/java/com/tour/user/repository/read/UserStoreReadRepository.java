package com.tour.user.repository.read;

import com.tour.user.vo.RequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreReadRepository {

    List<Map<String, Object>> storeSearch(Map<String, Object> params);
    List<Map<String, Object>> categoryList(Map<String, Object> params);
    List<Map<String, Object>> categoryDetail(Map<String, Object> params);
    List<Map<String, Object>> storeDetail(Map<String, Object> params);
    List<Map<String, Object>> categoryNameList();
    List<Map<String, Object>> categoryName(Map<String, Object> params);
    List<Map<String, Object>> experienceList(Map<String, Object> params);
    List<Map<String, Object>> experienceDetail(Map<String, Object> params);

}
