package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface PageService {
    Map<String, Object> myLike(RequestVO vo) throws Exception;
    Map<String, Object> myReview(RequestVO vo) throws Exception;
    Map<String, Object> myPage(RequestVO vo) throws Exception;
    Map<String, Object> myImageSave(MultipartHttpServletRequest multipart, RequestVO vo) throws Exception;
    Map<String, Object> myImageDelete(RequestVO vo) throws Exception;
    Map<String, Object> imageView(RequestVO vo) throws Exception;
    Map<String, Object> bannerView(RequestVO vo) throws Exception;
    Map<String, Object> storyList(RequestVO vo) throws Exception;
    Map<String, Object> storyView(RequestVO vo) throws Exception;
    Map<String, Object> storyRandom(RequestVO vo) throws Exception;

    /***************************************************************************/

    Map<String, Object> passList(RequestVO vo) throws Exception;
    Map<String, Object> passDayList(RequestVO vo) throws Exception;
    Map<String, Object> passSave(RequestVO vo) throws Exception;
    Map<String, Object> passDaySave(RequestVO vo)throws Exception;
    Map<String, Object> passStoreSave(RequestVO vo) throws Exception;
    Map<String, Object> passNameUpdate(RequestVO vo) throws Exception;

    Map<String, Object> passDelete(RequestVO vo) throws Exception;
    Map<String, Object> passDayUpdate(RequestVO vo) throws Exception;
    Map<String, Object> passDayDelete(RequestVO vo) throws Exception;
    Map<String, Object> passStoreUpdate(RequestVO vo) throws Exception;
    Map<String, Object> passStoreDelete(RequestVO vo) throws Exception;


}
