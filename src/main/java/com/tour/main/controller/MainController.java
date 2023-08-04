package com.tour.main.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Log4j2
@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public void start() throws Exception {
    }

    @RequestMapping("/admin")
    public ModelAndView indexPage() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/error")
    public ModelAndView indexPage(HttpServletResponse servlet){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code", servlet.getStatus());
        mv.setViewName("error");
        return mv;
    }




}
