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

    ModelAndView storeList() throws Exception;
    Map<String, Object> storeOne()  throws Exception;

    Map<String, Object>  jusoMapping(Map<String, Object> params) throws Exception;


}
