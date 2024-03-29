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
    List<Map<String, Object>> strReviewRead(Map<String, Object> params);
    List<String> bestKeyword (Map<String, Object> params);
    List<Map<String, Object>> reviewImageList(Map<String, Object> params);
    List<Map<String, Object>> chartList(Map<String, Object> params);
    List<Map<String, Object>> menuList(Map<String, Object> params);
    List<String> menuImageList(String mn_idx);
    List<Map<String, Object>> festivalBanner(Map<String, Object> params);
    Map<String, Object> menuOne(Map<String, Object> params);
    List<Map<String, Object>> addBanner(Map<String, Object> params);
}
