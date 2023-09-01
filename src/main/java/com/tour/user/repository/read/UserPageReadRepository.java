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
    List<String> reviewImageList(Map<String, Object> params);
}
