package com.tour.user.controller;

import com.tour.AES128;
import com.tour.JsonForm;
import com.tour.user.ExceptionController;
import com.tour.user.service.UserMemberServiceImpl;
import com.tour.user.vo.MemberVO;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.parser.Parser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserMemberController {

    @Value("#{aesConfig['key']}")
    private String key;

    private final UserMemberServiceImpl userMemberService;
    private Map<String, Object> setForm;

    @Autowired
    public UserMemberController(UserMemberServiceImpl userMemberService) {
        this.userMemberService = userMemberService;
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
    }

    @Autowired


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
    public Map<String, Object> userList(HttpServletResponse response){

        return userMemberService.userList();
    }

    /**
     * 특정회원조회
     */
    @RequestMapping("/userOne")
    @ResponseBody
    public  Map<String, Object> userOne(int mb_idx, HttpServletResponse response) throws Exception{
        return userMemberService.userOne(mb_idx);
    }

    /**
     *로그인
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> userLogin(@RequestBody Map<String, Object> params, HttpServletResponse response) throws Exception {
        return userMemberService.userLogin(params);
    }

    /**
     *회원가입
     */
    @RequestMapping("/join")
    @ResponseBody
    public Map<String, Object> userJoin(MemberVO vo, HttpServletResponse response){

        return userMemberService.userJoin(vo);
    }

    /**
     *중복체크
     */
    @RequestMapping("/dupChk")
    @ResponseBody
    public Map<String, Object> userDupChk(String mb_param, String mb_value){
        return userMemberService.userDupChk(mb_param, mb_value);
    }

    /**
     *특정데이터 조회
     */
    @RequestMapping("/dataOne")
    @ResponseBody
    public JSONObject dataOne(String mb_param, int mb_idx){
        return userMemberService.userDataOne(mb_param, mb_idx);
    }

    /**
     *이메일인증
     */
    @RequestMapping("/emailChk")
    @ResponseBody
    public JSONObject mailSend(String email){
        return userMemberService.mailSend(email);
    }

    /**
     *비밀번호 변경
     */
    @RequestMapping("/pwChange")
    @ResponseBody
    public JSONObject passwordChange(String mb_idx, String mb_pw){
        Map<String, Object> map = new HashMap<>();
        map.put("mb_idx", mb_idx);
        map.put("mb_pw", mb_pw);
        return userMemberService.passwordChange(map);
    }

    @GetMapping("/localList")
    @ResponseBody
    public Map<String, Object> localList(String mb_foreign){
        return userMemberService.localCategory(mb_foreign);
    }

    @GetMapping("/localChoice")
    @ResponseBody
    public Map<String, Object> localChoice(String set_1_code){
        JSONObject json = new JSONObject(userMemberService.localChoice(set_1_code));
        String res = "바보야 이거 아니야";
        return userMemberService.localChoice(set_1_code);
    }

    @PostMapping("/test")
    @ResponseBody
    public Map<String, Object> encryptTest(MemberVO vo){
        String val = vo.getReq();

        //JSONParser parser = new JSONParser();

        Map<String, Object> map = new HashMap<>();

        map.put("req", val);

        String res = "";
        try{
            AES128 aes = new AES128(key);
            val = aes.javaEncrypt(val);
            String test = aes.javaDecrypt(val);

            map.put("decrypt", test);
            res = "성공";

            map.put("encrypt", aes.javaEncrypt(val));
        }catch (Exception e){
            System.out.println(e.getMessage());
            res = "실패";
        }
        map.put("result", res);
        return map;
    }


}
