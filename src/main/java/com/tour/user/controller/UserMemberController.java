package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserMemberServiceImpl;
import com.tour.user.vo.RequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserMemberController {

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{address['ip']}")
    private String ip;

    private final UserMemberServiceImpl userMemberService;
    private final Map<String, Object> setForm;

    @Autowired
    public UserMemberController(UserMemberServiceImpl userMemberService) {
        this.userMemberService = userMemberService;
        this.setForm = new JsonForm(LocalDateTime.now()).setData();
    }

    private String getAccessToken(){
        List scopes = new ArrayList();
        /*scopes.add("https://www.googleapis.com/auth/firebase.messaging");*/
        return null;
    }


    /**
     *이용약관
     */
    @GetMapping("/agreement")
    @ResponseBody
    public List<Map<String, Object>> userAgreement(HttpServletRequest request){
        Map<String, Object> param = new HashMap<>();
        param.put("idx", null);
        param.put("ip",ip+request.getRequestURI());
        return userMemberService.userAgreement(param);
    }


    @RequestMapping("/agreement/view")
    public ModelAndView userAgreementView(String idx){
        ModelAndView mv = new ModelAndView();
        Map<String, Object> param = new HashMap<>();
        param.put("idx", idx);
        mv.setViewName("user/userAgreement");
        mv.addObject("agreement",userMemberService.userAgreement(param).get(0));
        return mv;
    }



    @RequestMapping("/testStory")
    public String andongStory(){
        return "admin/manage/summerNote";
    }

    /**
     * 전체회원조회
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public Map<String, Object> userList() throws Exception{
        return userMemberService.userList();
    }

    /**
     * 특정회원조회
     */
    @RequestMapping("/userOne")
    @ResponseBody
    public  Map<String, Object> userOne(RequestVO vo) throws Exception{
        return userMemberService.userOne(vo);
    }

    /**
     *로그인
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> userLogin(RequestVO vo) throws Exception{
        return userMemberService.userLogin(vo);
    }

    /**
     *회원가입
     */
    @RequestMapping("/join")
    @ResponseBody
    public Map<String, Object> userJoin(RequestVO vo) throws Exception{
        return userMemberService.userJoin(vo);
    }

    /**
     *중복체크
     */
    @RequestMapping("/dupChk")
    @ResponseBody
    public Map<String, Object> userDupChk(RequestVO vo) throws Exception {
        return userMemberService.userDupChk(vo);
    }

    /**
     *특정데이터 조회
     */
    @RequestMapping("/dataOne")
    @ResponseBody
    public Map<String, Object> dataOne(RequestVO vo) throws Exception{
        return userMemberService.userDataOne(vo);
    }

    /**
     *이메일인증
     */
    @RequestMapping("/emailChk")
    @ResponseBody
    public Map<String, Object> mailSend(RequestVO vo) throws Exception{
        return userMemberService.mailSend(vo);
    }

    /**
     *비밀번호 변경
     */
    @RequestMapping("/pwChange")
    @ResponseBody
    public Map<String, Object> passwordChange(RequestVO vo) throws Exception{
        return userMemberService.passwordChange(vo);
    }

    @PostMapping("/localList")
    @ResponseBody
    public Map<String, Object> localList(RequestVO vo) throws Exception{
        return userMemberService.localCategory(vo);
    }

    @PostMapping("/localChoice")
    @ResponseBody
    public Map<String, Object> localChoice(RequestVO vo) throws Exception{
        return userMemberService.localChoice(vo);
    }

    @PostMapping("/test")
    @ResponseBody
    public Map<String, Object> encryptTest(RequestVO vo) throws Exception{
        return userMemberService.test(vo);
    }

    @PostMapping("/sessionChk")
    @ResponseBody
    public Map<String, Object> sessionChk(RequestVO vo) throws Exception{
        return userMemberService.sessionChk(vo);
    }

    @PostMapping("/emailUser")
    @ResponseBody
    public Map<String, Object> emailUser(RequestVO vo) throws Exception{
        return userMemberService.memberEmailChk(vo);
    }

    @PostMapping("/alarm/check")
    @ResponseBody
    public Map<String, Object> alarmCheck(RequestVO vo) throws Exception{
        return userMemberService.alarmCheck(vo);
    }

    @PostMapping("/login/sns")
    @ResponseBody
    public Map<String, Object> snsUserLogin(RequestVO vo) throws Exception{
        return userMemberService.snsUserLogin(vo);
    }

    @PostMapping("/push/list")
    @ResponseBody
    public Map<String, Object> userPushList(RequestVO vo) throws Exception{
        return userMemberService.userPushList(vo);
    }
}
