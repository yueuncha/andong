package com.tour.admin.service;

import java.util.List;
import java.util.Map;

public interface AdminStoreService {

    List<Map<String, Object>> adminStoreList(Map<String, Object> params) throws Exception;
    List<Map<String, Object>> storeViewOne(int str_idx) throws Exception;
    List<Map<String, Object>> adminCategoryList(Map<String, Object> params) throws Exception;
    Map<String, Object> adminCategoryOne(int ct_idx) throws Exception;
    List<Map<String, Object>> adminCategoryEtc() throws Exception;


}
