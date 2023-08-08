package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserMemberServiceImpl;
import com.tour.user.vo.MemberVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserMemberController {

    private Map<String, Object> setForm;
    private final UserMemberServiceImpl userMemberService;

    @Autowired
    public UserMemberController(UserMemberServiceImpl userMemberService) {
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
        this.userMemberService = userMemberService;
    }

    /**
     *이용약관
     */
    @RequestMapping("/agreement")
    public ModelAndView userAgreement(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/userAgreement");
        mv.addObject("agreement",userMemberService.userAgreement());
        return mv;
    }

    /**
     * 전체회원조회
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public JSONObject userList() throws Exception{
        return userMemberService.userList(setForm);
    }

    /**
     * 특정회원조회
     */
    @RequestMapping("/userOne")
    @ResponseBody
    public JSONObject userOne(int mb_idx) throws Exception{
        return userMemberService.userOne(mb_idx, setForm);
    }

    /**
     *로그인
     */
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject userLogin(String mb_id, String mb_pw){
        return userMemberService.userLogin(mb_id, mb_pw, setForm);
    }

    /**
     *회원가입
     */
    @RequestMapping("/join")
    @ResponseBody
    public JSONObject userJoin(MemberVO vo){
        return userMemberService.userJoin(vo, setForm);
    }

    /**
     *중복체크
     */
    @RequestMapping("/dupChk")
    @ResponseBody
    public JSONObject userDupChk(String mb_param, String mb_value){
        return userMemberService.userDupChk(mb_param, mb_value, setForm);
    }

    /**
     *특정데이터 조회
     */
    @RequestMapping("/dataOne")
    @ResponseBody
    public JSONObject dataOne(String mb_param, int mb_idx){
        return userMemberService.userDataOne(mb_param, mb_idx, setForm);
    }

    /**
     *이메일인증
     */
    @RequestMapping("/emailChk")
    @ResponseBody
    public JSONObject mailSend(String email){
        return userMemberService.mailSend(email, setForm);
    }




}
