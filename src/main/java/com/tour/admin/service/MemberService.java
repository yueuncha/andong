package com.tour.admin.service;


import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberService {

    int managerSave(Map<String, Object> params);
    Map<String, Object> managerOne(int mb_idx);
    ModelAndView managerView();
    int mailSend(String email);

    List<Map<String, Object>> userList(Map<String, Object> params) throws Exception;
/*
    ModelAndView storeList() throws Exception;
    Map<String, Object> storeOne()  throws Exception;

    Map<String, Object>  jusoMapping(Map<String, Object> params) throws Exception;
*/
    /********************************************************************************/

    List<Map<String, Object>> inquiryList(Map<String, Object> params) throws Exception;
    Map<String, Object> inquiryView(int inq_idx) throws Exception;
    int inquiryWrite(int iq_idx, String inquiryAnswer) throws Exception;

    List<Map<String, Object>> reportList(Map<String, Object> params) throws Exception;
    Map<String, Object> reportView(int report_idx) throws Exception;


    //// faq
    List<Map<String, Object>> faqList() throws Exception;
    Map<String, Object> faqView(int faq_idx) throws Exception;
    int faqWrite(Map<String, Object> params) throws Exception;
    int faqUpdate(Map<String, Object> params) throws Exception;
    int faqDelete(int faq_idx) throws Exception;



}
