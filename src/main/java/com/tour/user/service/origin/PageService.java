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
    Map<String, Object> imageView(RequestVO vo) throws Exception;


}
