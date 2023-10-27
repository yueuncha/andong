package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserPageReadRepository {
    List<Map<String, Object>> myLike(Map<String, Object> params) ;
    List<Map<String, Object>> myReview(Map<String, Object> params) ;
    Map<String, Object> myPageCnt(Map<String, Object> params);
    List<Map<String, Object>> myImageLoad(Map<String, Object> params);
    List<Map<String, Object>> reviewImageList(Map<String, Object> params);
    List<Map<String, Object>> bannerList(Map<String, Object> params);
    List<Map<String, Object>> storyList(Map<String, Object> params);

    List<Map<String, Object>> storyRandom(Map<String, Object> newParams);

    Map<String, Object> storyView(Map<String, Object> newParams);

    /**************************************************************************/

    List<Map<String, Object>> passList(Map<String, Object> params);
    List<String> passImages(Map<String, Object> params);
    List<Map<String, Object>> passDayList(Map<String, Object> params);

    List<Map<String, Object>> bestPassList(Map<String, Object> params);
    List<Map<String, Object>> inquiryCategory(Map<String, Object> params);
    List<Map<String, Object>> inquiryList(Map<String, Object> params);
    List<Map<String, Object>> inquiryOne(Map<String, Object> params);
    List<Map<String, Object>> myBookMark(Map<String, Object> params);



}
