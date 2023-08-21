package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface StoreService {
    /******************************************************************************/
    //업체조회
    JSONObject storeList();
    //검색
    List<Map<String, Object>> storeSearch(RequestVO vo);
    Map<String, Object> categoryList(RequestVO vo) throws Exception;
    Map<String, Object> categoryDetail(RequestVO vo) throws Exception;
    Map<String, Object> storeDetail(RequestVO vo) throws Exception;
    Map<String, Object> storeView(RequestVO vo) throws Exception;
    Map<String, Object> storeLike(RequestVO vo) throws Exception;
    Map<String, Object> strReviewCreate(RequestVO vo) throws Exception;





}
