package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/store")
@Controller
public class UserStoreController {

    private final UserStoreServiceImpl storeService;

    @Autowired
    public UserStoreController(UserStoreServiceImpl storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/category")
    @ResponseBody
    public List<Map<String, Object>> categoryList(String ct_parent, String lang){
        return storeService.categoryList(ct_parent, lang);
    }

    @PostMapping("/storeSearch")
    @ResponseBody
    public List<Map<String, Object>> storeSearch(Map<String, Object> params){
        return storeService.storeSearch(params);
    }

    @GetMapping("/categoryDetail")
    @ResponseBody
    public List<Map<String, Object>> categoryDetail(String str_category, String lang){
        return storeService.categoryDetail(str_category, lang);
    }

    @GetMapping("/storeDetail")
    @ResponseBody
    public List<Map<String, Object>> storeDetail(String str_idx, String lang){
        return storeService.storeDetail(str_idx, lang);
    }

    @GetMapping("/storeViewCnt")
    @ResponseBody
    public Map<String, Object> storeView(String str_idx){
        return storeService.storeView(str_idx);
    }

    @PostMapping("/storeLike")
    @ResponseBody
    public Map<String, Object> storeLike(@RequestBody Map<String, Object> params){
        return storeService.storeLike(params);
    }


}
