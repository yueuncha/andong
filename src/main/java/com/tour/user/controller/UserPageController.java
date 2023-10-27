package com.tour.user.controller;


import com.tour.user.service.UserPageServiceImpl;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Collections;
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
    }

    @PostMapping("/my/imageDelete")
    @ResponseBody
    public Map<String, Object> myImageDelete(RequestVO vo) throws Exception{
        return userPageService.myImageDelete(vo);
    }

    @PostMapping("/my/mark")
    @ResponseBody
    public Map<String, Object> myBookMark(RequestVO vo) throws Exception{
        return userPageService.myBookMark(vo);
    }


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

    @PostMapping("/page/banner")
    @ResponseBody
    public Map<String, Object> bannerView(RequestVO vo) throws Exception{
        return userPageService.bannerView(vo);
    }

    @PostMapping("/page/storyList")
    @ResponseBody
    public Map<String, Object> storyList(RequestVO vo) throws Exception{
        return userPageService.storyList(vo);
    }

    @PostMapping("/page/storyView")
    @ResponseBody
    public Map<String, Object> storyView(RequestVO vo) throws Exception{
        return userPageService.storyView(vo);
    }


    @PostMapping("/page/storyRandom")
    @ResponseBody
    public Map<String, Object> storyRandom(RequestVO vo) throws Exception{
        return userPageService.storyRandom(vo);
    }

    @PostMapping("/pass/list")
    @ResponseBody
    public Map<String, Object> passList(RequestVO vo) throws Exception{
        return userPageService.passList(vo);
    }

    @PostMapping("/pass/day")
    @ResponseBody
    public Map<String, Object> passDayList(RequestVO vo) throws Exception{
        return userPageService.passDayList(vo);
    }

    @PostMapping("/pass/save")
    @ResponseBody
    public Map<String, Object> passSave(RequestVO vo) throws Exception{
        return userPageService.passSave(vo);
    }

    @PostMapping("/pass/daySave")
    @ResponseBody
    public Map<String, Object> passDaySave(RequestVO vo) throws Exception{
        return userPageService.passDaySave(vo);
    }

    @PostMapping("/pass/storeSave")
    @ResponseBody
    public Map<String, Object> passStoreSave(RequestVO vo) throws Exception{
        return userPageService.passStoreSave(vo);
    }

    @PostMapping("/pass/delete")
    @ResponseBody
    public Map<String, Object> passDelete(RequestVO vo) throws Exception{
        return userPageService.passDelete(vo);
    }

    @PostMapping("/pass/dayDelete")
    @ResponseBody
    public Map<String, Object> passDayDelete(RequestVO vo) throws Exception{
        return userPageService.passDayDelete(vo);
    }

    @PostMapping("/pass/storeDelete")
    @ResponseBody
    public Map<String, Object> passStoreDelete(RequestVO vo) throws Exception{
        return userPageService.passStoreDelete(vo);
    }


    @PostMapping("/pass/update")
    @ResponseBody
    public Map<String, Object> passNameUpdate(RequestVO vo) throws Exception{
        return userPageService.passNameUpdate(vo);
    }

    @GetMapping("/pass/best")
    @ResponseBody
    public Map<String, Object> bestPassList() throws Exception{
        RequestVO vo = new RequestVO();
        vo.setReq("{}");
        return userPageService.bestPassList(vo);
    }

    @GetMapping("/quest/category")
    @ResponseBody
    public Map<String, Object> questCategory(String type) throws Exception{
        RequestVO vo = new RequestVO();
        JSONObject json = new JSONObject();
        vo.setReq(json.toJSONString(Collections.singletonMap("type",type)));
        return userPageService.questCategory(vo);
    }

    @PostMapping("/quest/write")
    @ResponseBody
    public Map<String, Object> questInsert(RequestVO vo) throws Exception{
        return userPageService.questInsert(vo);
    }

    @PostMapping("/quest/list")
    @ResponseBody
    public Map<String, Object> questList(RequestVO vo) throws Exception{
        return userPageService.questList(vo);
    }

    @PostMapping("/quest/one")
    @ResponseBody
    public Map<String, Object> questOne(RequestVO vo) throws Exception{
        return userPageService.questOne(vo);
    }

    @PostMapping("/quest/update")
    @ResponseBody
    public Map<String, Object> questUpdate(RequestVO vo) throws Exception{
        return userPageService.questUpdate(vo);
    }

    @PostMapping("/quest/delete")
    @ResponseBody
    public Map<String, Object> questDelete(RequestVO vo) throws Exception{
        return userPageService.inquiryDelete(vo);
    }




}
