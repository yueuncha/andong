package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequestMapping("/store")
@Controller
public class UserStoreController {

    private final UserStoreServiceImpl storeService;

    @Autowired
    public UserStoreController(UserStoreServiceImpl storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/category")
    @ResponseBody
    public Map<String, Object> categoryList(RequestVO vo) throws Exception{
        return storeService.categoryList(vo);
    }

    @PostMapping("/storeSearch")
    @ResponseBody
    public List<Map<String, Object>> storeSearch(RequestVO vo) throws Exception{
        return storeService.storeSearch(vo);
    }

    @PostMapping("/categoryDetail")
    @ResponseBody
    public Map<String, Object> categoryDetail(RequestVO vo) throws Exception{
        return storeService.categoryDetail(vo);
    }

    @PostMapping("/storeDetail")
    @ResponseBody
    public Map<String, Object> storeDetail(RequestVO vo) throws Exception{
        return storeService.storeDetail(vo);
    }

    @PostMapping("/storeViewCnt")
    @ResponseBody
    public Map<String, Object> storeView(RequestVO vo) throws Exception{
        return storeService.storeView(vo);
    }

    @PostMapping("/storeLike")
    @ResponseBody
    public Map<String, Object> storeLike(RequestVO vo) throws Exception{
        return storeService.storeLike(vo);
    }

    @PostMapping("/experList")
    @ResponseBody
    public Map<String, Object> experienceList(RequestVO vo) throws Exception{
        return storeService.experienceList(vo);
    }

    @PostMapping("/experDetail")
    @ResponseBody
    public Map<String, Object> experienceDetail(RequestVO vo) throws Exception{
        return storeService.experienceDetail(vo);
    }

    @PostMapping("/experView")
    @ResponseBody
    public Map<String, Object> experienceView(RequestVO vo) throws Exception{
        return storeService.experienceView(vo);
    }

    @PostMapping("/festivalList")
    @ResponseBody
    public Map<String, Object> festivalList(RequestVO vo) throws Exception{
        return storeService.festivalList(vo);
    }

    @PostMapping("/festivalView")
    @ResponseBody
    public Map<String, Object> festivalView(RequestVO vo) throws Exception{
        return storeService.festivalView(vo);
    }

    @PostMapping("/reviewWrite")
    @ResponseBody
    public Map<String, Object> strReviewWrite(MultipartHttpServletRequest multipart, RequestVO vo) throws Exception{
        return storeService.strReviewCreate(multipart, vo);
    }







    /*
    * 이미지 조회(해결)/ 메뉴 조회 / 리뷰 별점/ 통계/ 패스 저장 / 패스 추가 / 일정 추가 / 축제체험 조회 /
    *
    * */




}
