package com.tour.admin.controller;

import com.tour.admin.service.MemberServiceImpl;
import com.tour.user.vo.RequestVO;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
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

    @RequestMapping("/login")
    public ModelAndView manageLogin() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }


    @RequestMapping("/user")
    public ModelAndView userView() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", memberServiceImpl.userList(Collections.singletonMap("type",1)));
        mv.setViewName("admin/member/userView");
        return mv;
    }

    @PostMapping("/user/filter")
    @ResponseBody
    public List<Map<String, Object>> userFilterList(@RequestBody Map<String, Object> value) throws Exception{
        return memberServiceImpl.userList(value);
    }

    @RequestMapping("/inquiry")
    public ModelAndView inquiryView() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", memberServiceImpl.inquiryList(new HashMap<>()));
        mv.setViewName("admin/member/inquiryView");
        return mv;
    }

    @RequestMapping("/inquiry/view")
    public ModelAndView inquiryViewOne(int iq_idx) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", memberServiceImpl.inquiryView(iq_idx));
        mv.setViewName("admin/member/inquiryViewOne");
        return mv;
    }

    @RequestMapping("/inquiry/write")
    public ModelAndView inquiryWrite(int iq_idx, String inquiryAnswer) throws Exception{
        ModelAndView mv = new ModelAndView();
        memberServiceImpl.inquiryWrite(iq_idx, inquiryAnswer);
        mv.setViewName("load");
        mv.addObject("url", "/admin/inquiry/view?iq_idx="+iq_idx);
        return mv;
    }


    @RequestMapping("/report")
    public ModelAndView reportView() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/reportView");
        mv.addObject("list", memberServiceImpl.reportList(new HashMap<>()));
        return mv;
    }

    @RequestMapping("/report/view")
    public ModelAndView reportViewOne(int report_idx) throws Exception{
        ModelAndView mv = new ModelAndView();

        mv.addObject("list", memberServiceImpl.reportView(report_idx));
        mv.setViewName("admin/member/reportViewOne");
        return mv;
    }

    // faq 조회
    @RequestMapping("/faq")
    public ModelAndView faqList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/faqList");
        mv.addObject("list", memberServiceImpl.reportList(new HashMap<>()));
        return mv;
    }

// /admin/user/faq
    // faq 상세
    @RequestMapping("/faq/view")
    public ModelAndView faqView() throws Exception{
        return null;
    }

    // faq 작성
    @RequestMapping("/faq/write")
    public String faqWrite() throws Exception{
        return "";
    }

    // faq 수정
    @RequestMapping("/faq/update")
    public ModelAndView faqUpdate() throws Exception{
        return null;
    }

    // faq 삭제
    @RequestMapping("/faq/delete")
    public String faqDelete() throws Exception{
        return "";
    }
















    /********************************************************************************************************/

    @GetMapping("/data/json")
    @ResponseBody
    public JSONObject jsonTest(){
        String file = "/usr/local/tomcat9/webapps/data/file/json/1.json";
        System.out.println("file : " + file);
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonStr = null;
        try {
            jsonStr = (String) parser.parse(reader);
            json = (JSONObject) parser.parse(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return json;
    }

    @GetMapping("/data/json/arr")
    @ResponseBody
    public JSONObject jsonTestArr(String fileName , int type) throws Exception{
        String jsonStr;

        String testBaseUrl = "http://52.78.144.77/data/excel/";
        String serverBaseUrl = " https://boss.mukkebi.com/data/excel/";
        String fileNameStr = "shopdata_"+fileName+".json";
        String urlStr = "";

        if(type == 1){
            urlStr = testBaseUrl + fileNameStr;
        }else{
            urlStr = serverBaseUrl + fileNameStr;
        }

        URL url = new URL(urlStr);
        JSONParser parser = new JSONParser();
        URLConnection  in = null;
        in = url.openConnection();
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(in.getInputStream())
        );

        jsonStr = buffer.readLine();
        jsonStr = (String) parser.parse(jsonStr);
        JSONObject json = (JSONObject) parser.parse(jsonStr);

        return json;
    }

    /********************************************************************************************************/






}
