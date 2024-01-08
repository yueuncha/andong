package com.tour.admin.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminStoreReadRepository {
    List<Map<String, Object>> storeListAll(Map<String, Object> params);
    List<Map<String, Object>> storeViewOne(int str_idx);
    List<Map<String, Object>> adminCategoryList();
    Map<String, Object> adminCategoryOne(int ct_idx);
    List<Map<String, Object>> adminCategoryStepList(Map<String, Object> params);
}
