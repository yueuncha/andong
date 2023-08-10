package com.tour.user.service.origin;

import org.json.simple.JSONObject;

import java.util.Map;

public interface StoreService {
    /******************************************************************************/
    //업체조회
    JSONObject storeList();
    JSONObject storeSearch(Map<String, Object> param);
}
