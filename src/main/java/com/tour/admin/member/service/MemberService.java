package com.tour.admin.member.service;


import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public interface MemberService {

    int managerSave(Map<String, Object> params);
    Map<String, Object> managerOne(int mb_idx);
    ModelAndView managerView();
    int mailSend(String email);


}
