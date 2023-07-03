package com.tour.user.board.controller;

import com.tour.JsonForm;
import com.tour.user.board.service.UserMainServiceImpl;
import com.tour.user.board.vo.MemberVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserMainController{

    private Map<String, Object> setForm;
    private final UserMainServiceImpl userMainService;

    @Autowired
    public UserMainController(UserMainServiceImpl userMainService) {
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
        this.userMainService = userMainService;
    }

    @RequestMapping("/agreement")
    public ModelAndView userAgreement(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/userAgreement");
        mv.addObject("agreement",userMainService.userAgreement());
        return mv;
    }

    @RequestMapping("/listAll")
    @ResponseBody
    public JSONObject userList() throws Exception{
        return userMainService.userList(setForm);
    }

    @RequestMapping("/userOne")
    @ResponseBody
    public JSONObject userOne(int mb_idx) throws Exception{
        return userMainService.userOne(mb_idx, setForm);
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject userLogin(String mb_id, String mb_pw){
        return userMainService.userLogin(mb_id, mb_pw, setForm);
    }

    @RequestMapping("/join")
    @ResponseBody
    public JSONObject userJoin(MemberVO vo){
        return userMainService.userJoin(vo, setForm);
    }

    @RequestMapping("/dupChk")
    @ResponseBody
    public JSONObject userDupChk(String mb_param, String mb_value){
        return userMainService.userDupChk(mb_param, mb_value, setForm);
    }

    @RequestMapping("/dataOne")
    @ResponseBody
    public JSONObject dataOne(String mb_param, int mb_idx){
        return userMainService.userDataOne(mb_param, mb_idx, setForm);
    }



}
