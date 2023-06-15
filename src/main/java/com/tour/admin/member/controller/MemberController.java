package com.tour.admin.member.controller;

import com.tour.admin.member.service.MemberServiceImpl;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/member/")
@Controller
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @RequestMapping("userView")
    public ModelAndView userView(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/userView");
        return mv;
    }
}
