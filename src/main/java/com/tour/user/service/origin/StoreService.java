package com.tour.user.service.origin;

import org.json.simple.JSONObject;

import java.util.Map;

public interface StoreService {
    /******************************************************************************/
    //업체조회
    JSONObject storeList();
    //검색
    JSONObject storeSearch(Map<String, Object> param);
    JSONObject categoryList();
    JSONObject categoryDetail();
    JSONObject storeDetail();

    JSONObject storeView();
    JSONObject storeLike();
    JSONObject storeReview();


}
