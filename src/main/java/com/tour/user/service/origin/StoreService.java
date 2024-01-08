package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface StoreService {
    /******************************************************************************/
    //업체조회
    JSONObject storeList();
    //검색
    Map<String, Object> storeSearch(RequestVO vo)throws Exception;
    Map<String, Object> categoryList(RequestVO vo) throws Exception;
    Map<String, Object> categoryDetail(RequestVO vo) throws Exception;
    Map<String, Object> storeDetail(RequestVO vo) throws Exception;
    Map<String, Object> storeView(RequestVO vo) throws Exception;
    Map<String, Object> storeLike(RequestVO vo) throws Exception;
    Map<String, Object> strReviewCreate(MultipartHttpServletRequest multipart, RequestVO vo) throws Exception;

    Map<String, Object> categoryNameList(RequestVO vo) throws Exception;
    Map<String, Object> categoryName(RequestVO vo) throws Exception;

    Map<String, Object> mainExperience(RequestVO vo) throws Exception;
    Map<String, Object> experienceList(RequestVO vo) throws Exception;
    Map<String, Object> experienceDetail(RequestVO vo) throws Exception;

    Map<String, Object> experienceView(RequestVO vo) throws  Exception;
    Map<String, Object> festivalList(RequestVO vo) throws  Exception;
    Map<String, Object> festivalView(RequestVO vo) throws  Exception;

    Map<String, Object> bestKeyword(RequestVO vo) throws Exception;
    Map<String, Object> chartList(RequestVO vo) throws Exception;

    Map<String, Object> reviewList(RequestVO vo) throws Exception;
    Map<String, Object> reviewUpdate(MultipartHttpServletRequest multipart, RequestVO vo) throws Exception;
    Map<String, Object> reviewDelete(RequestVO vo) throws Exception;

    Map<String, Object> rvImageDelete(RequestVO vo) throws Exception;

    Map<String, Object> menuList(RequestVO vo) throws Exception;
    Map<String, Object> festivalBanner(RequestVO vo) throws Exception;
    
    Map<String, Object> menuOne(RequestVO vo) throws Exception;
    
    
    
    /**********************************************************************************/
    // 안동이야기 테스트
    Map<String, Object> storyTextSave(Map<String, Object> values) throws Exception;



}
