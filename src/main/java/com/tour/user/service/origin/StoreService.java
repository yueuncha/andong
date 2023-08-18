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
    Map<String, Object> categoryList(Map<String, Object> params);
    Map<String, Object> categoryDetail(String str_category, String lang, String mb_idx);
    Map<String, Object> storeDetail(String str_idx, String lang, String mb_idx);
    Map<String, Object> storeView(String str_idx, String mb_idx);
    Map<String, Object> storeLike(Map<String, Object> params);
    Map<String, Object> strReviewCreate(Map<String, Object> params);





}
