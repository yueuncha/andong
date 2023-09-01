package com.tour.user.controller;


import com.tour.user.service.UserPageServiceImpl;
import com.tour.user.vo.RequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

@Controller
public class UserPageController {
    @Value("#{aesConfig['key']}")
    private String key;

    private final UserPageServiceImpl userPageService;
    private Map<String, Object> setForm;

    @Autowired
    public UserPageController(UserPageServiceImpl userPageService) {
        this.userPageService = userPageService;
    }


    @PostMapping("/my/page")
    @ResponseBody
    public Map<String, Object> myPage(RequestVO vo) throws Exception{
        return userPageService.myPage(vo);
    }

    @PostMapping("/my/imageSave")
    @ResponseBody
    public Map<String, Object> myImageSave(MultipartHttpServletRequest files, RequestVO vo) throws Exception{
        return userPageService.myImageSave(files,vo);
    }

    @PostMapping("/my/imageView")
    @ResponseBody
    public Map<String, Object> imageView(RequestVO vo) throws Exception{
        return userPageService.imageView(vo);
    }{}


    @PostMapping("/my/like")
    @ResponseBody
    public Map<String, Object> myLike(RequestVO vo) throws Exception{
        return userPageService.myLike(vo);
    }

    @PostMapping("/my/review")
    @ResponseBody
    public Map<String, Object> myReview(RequestVO vo) throws Exception{
        return userPageService.myReview(vo);
    }

}
