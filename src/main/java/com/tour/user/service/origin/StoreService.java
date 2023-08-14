package com.tour.user.service.origin;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface StoreService {
    /******************************************************************************/
    //업체조회
    JSONObject storeList();
    //검색
    List<Map<String, Object>> storeSearch(Map<String, Object> params);
    List<Map<String, Object>> categoryList(String ct_parent, String lang);
    List<Map<String, Object>> categoryDetail(String str_category, String lang);
    List<Map<String, Object>> storeDetail(String str_idx, String lang);
    Map<String, Object> storeView(String str_idx);
    Map<String, Object> storeLike(Map<String, Object> params);
    JSONObject storeReview();


}
