package com.tour.admin.controller;

import com.tour.admin.service.AdminStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StoreController {

    private AdminStoreServiceImpl service;
    @Value("#{address['ip']}")
    private String ip;

    @Autowired
    public StoreController(AdminStoreServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/admin/store")
    public ModelAndView storeListAll(int type) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/store/storeView");
        mv.addObject("list", service.adminStoreList(Collections.singletonMap("type", type)));
        mv.addObject("type", type);
        return mv;
    }


    @RequestMapping("/admin/store/view")
    public ModelAndView storeViewOne(int str_idx) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/store/storeViewOne");
        mv.addObject("list", service.storeViewOne(str_idx));
        mv.addObject("url", ip);
        return mv;
    }

    @RequestMapping("/admin/category/step")
    public ModelAndView categoryHighList(int type) throws Exception{
        ModelAndView mv = new ModelAndView();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        mv.addObject("list", service.adminCategoryList(params));
        if(type != 1){
            params.replace("type", "1");
            mv.addObject("category", service.adminCategoryList(params));
        }
        mv.setViewName("admin/store/categoryStep");
        return mv;
    }

    @RequestMapping("/admin/category/etc")
    public ModelAndView categoryEtc() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/store/categoryEtc");
        return mv;
    }




    @PostMapping("/admin/category/view")
    @ResponseBody
    public Map<String, Object> cateogoryViewOne(@RequestBody Map<String, Object> param) throws Exception{
        int ct_idx = 1;
        if(param.containsKey("ct_idx")){
            ct_idx = Integer.parseInt(String.valueOf(param.get("ct_idx")));
        }
        return service.adminCategoryOne(ct_idx);
    }

    @RequestMapping("/admin/review")
    public ModelAndView reviewList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/store/reviewView");
        return mv;
    }


    // /admin/category/stepHigh
    // 상위 카테고리 조회
    // 상위 카테고리 추가
    // 상위 카테고리 수정
    // 상위 카테고리 삭제


    // /admin/category/stepRow
    // 하위 카테고리 조회
    // 하위 카테고리에 해당되는 업체 리스트 조회
    // 하위 카테고리 추가
    // 하위 카테고리 수정
    // 하위 카테고리 삭제



    // /admin/store/tour
    // 관광지 목록 조회(카테고리 같이 조회)
    // 관광지 상세 조회
    // 관광지 추가
    // 관광지 수정
    // 관광지 삭제

    // /admin/store/food
    // 맛집 목록 조회(카테고리 같이 조회)
    // 맛집 상세 조회
    // 맛집 추가
    // 맛집 수정
    // 맛집 삭제

    // /admin/store/rest
    // 숙박 목록 조회(카테고리 같이 조회)
    // 숙박 상세 조회
    // 숙박 추가
    // 숙박 수정
    // 숙박 삭제







}
