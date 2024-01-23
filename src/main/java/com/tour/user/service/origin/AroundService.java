package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface AroundService {

    Map<String, Object> aroundView(RequestVO vo) throws Exception;
    Map<String, Object> aroundOne(RequestVO vo) throws Exception;
    Map<String, Object> parkingList(RequestVO vo) throws Exception;
    Map<String, Object> parkingOne(RequestVO vo) throws Exception;
    Map<String, Object> stampTourList(RequestVO vo) throws Exception;
    Map<String, Object> stampOne(RequestVO vo) throws Exception;
    Map<String, Object> postingViewList(RequestVO vo) throws Exception;
    Map<String, Object> postingViewOne(RequestVO vo) throws Exception;
    Map<String, Object> postingViewInsert(RequestVO vo) throws Exception;
    Map<String, Object> postLike(RequestVO vo) throws Exception;
    Map<String, Object> postBookmark(RequestVO vo) throws Exception;
    Map<String, Object> postInsert(MultipartHttpServletRequest request, RequestVO vo) throws Exception;
    Map<String, Object> postUpdate(MultipartHttpServletRequest multipart,RequestVO vo) throws Exception;
    Map<String, Object> postDelete(RequestVO vo) throws Exception;
    Map<String, Object> reportPost(RequestVO vo) throws Exception;

    Map<String, Object> stampGiveCheck(RequestVO vo) throws Exception;
}
