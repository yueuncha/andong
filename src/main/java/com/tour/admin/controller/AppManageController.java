package com.tour.admin.controller;

import com.tour.admin.service.AppManageServiceImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppManageController {

    private AppManageServiceImpl service;
    @Value("#{address['ip']}")
    private String ip;

    @Autowired
    public AppManageController(AppManageServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/admin/story")
    public ModelAndView andongStoryList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", service.andongStoryList());
        mv.setViewName("admin/manage/storyList");
        return  mv;
    }

    @RequestMapping("/admin/story/view")
    public ModelAndView andongStoryView(int tour_idx) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", service.andongStoryView(tour_idx));
        mv.addObject("url", ip);
        mv.setViewName("admin/manage/storyViewOne");
        return mv;
    }

    @RequestMapping("/admin/story/write")
    public ModelAndView andongStoryWrite() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", ip);
        mv.setViewName("admin/manage/storyWrite");
        return  mv;
    }

    @RequestMapping("/admin/banner")
    public ModelAndView bannerLoad() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manage/bannerView");
        mv.addObject("list", service.bannerLoad());
        return mv;
    }

    @RequestMapping("/admin/banner/add")
    @ResponseBody
    public ModelAndView bannerLoadAdd() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("load");
        mv.addObject("url", ip+"/admin/banner");
        service.bannerLoadAdd();
        return  mv;
    }

    // banner 수정
    @RequestMapping("/admin/banner/update")
    @ResponseBody
    public int bannerUpdate(@RequestBody Map<String, Object> banner) throws Exception{
        return service.bannerUpdate(banner);
    }

    // banner 삭제
    @GetMapping("/admin/banner/delete")
    @ResponseBody
    public ModelAndView bannerDelete(int bn_idx) throws Exception{
        service.bannerDelete(bn_idx);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("load");
        mv.addObject("url", ip+"/admin/banner");
        return mv;
    }


    @RequestMapping("/admin/stamp")
    public ModelAndView stampList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manage/stampView");
        mv.addObject("list", service.stampList());
        return  mv;
    }

    @RequestMapping("/admin/stamp/user")
    public ModelAndView stampUserList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manage/stampUserList");
        mv.addObject("list", service.stampUserList());
        return  mv;
    }

    @RequestMapping("/admin/stats")
    public ModelAndView allStats() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manage/statsView");
        return  mv;
    }

    @RequestMapping("/admin/board")
    public ModelAndView boardList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", service.boardUserList());
        mv.setViewName("admin/manage/boardList");
        return  mv;
    }

    @RequestMapping("/admin/tour")
    public ModelAndView tourList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", service.recommendTourList());
        mv.setViewName("admin/manage/tourList");
        return  mv;
    }

    @RequestMapping("/admin/push")
    public ModelAndView pushSend() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/manage/pushSend");
        return  mv;
    }

    @RequestMapping("/admin/push/list")
    public ModelAndView pushSendList() throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", service.pushSendList());
        mv.setViewName("admin/manage/managePushList");
        return  mv;
    }



    // /admin/board/?
    // 피드 조회
    // 피드 상세
    // 피드 삭제

    // /admin/tour/?
    // 추천코스 조회
    // 추천코스 추가 - 관광지 검색해서 추가
    // 관광지 검색
    // 추천코스 수정
    // 추천코스 삭제

    // /admin/stamp
    // 스탬프투어 조회
    // 스탬프투어 추가
    // 스탬프투어 수정
    // 스탬프투어 상세
    // 스탬프투어 삭제


    // /admin/stamp/user
    // 스탬프투어 사용자 현황
    // 스탬프투어 기간 만료 설정

    // /admin/push
    // 알림 발송
    // 발송 기록 조회





}
