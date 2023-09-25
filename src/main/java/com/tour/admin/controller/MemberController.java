package com.tour.admin.controller;

import com.tour.admin.service.MemberServiceImpl;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @RequestMapping("/user")
    public ModelAndView userView(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/userView");
        return mv;
    }

    @RequestMapping("/store")
    public ModelAndView storeView() throws Exception{
        ModelAndView mv = memberServiceImpl.storeList();
        mv.setViewName("admin/member/storeView");
        return mv;
    }

    @RequestMapping("/store/one")
    @ResponseBody
    public Map<String, Object> storeVie1w() throws Exception{
        return memberServiceImpl.storeOne();
    }

    @RequestMapping("/manager")
    public ModelAndView managerView(){
        return memberServiceImpl.managerView();
    }


    @RequestMapping("/managerSave")
    @ResponseBody
    public int managerSave(@RequestBody HashMap<String, Object> params){
        return memberServiceImpl.managerSave(params);
    }

    @RequestMapping("/managerSelectOne")
    @ResponseBody
    public Map<String, Object> managerOne(@RequestBody int mb_idx){
        return memberServiceImpl.managerOne(mb_idx);
    }


    @PostMapping("/store/xy")
    @ResponseBody
    public Map<String, Object>  jusoMapping(@RequestBody Map<String, Object> params) throws Exception{
        return memberServiceImpl.jusoMapping(params);
    }




}
