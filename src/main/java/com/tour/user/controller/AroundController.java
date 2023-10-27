package com.tour.user.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.tour.user.vo.RequestVO;
import com.tour.user.service.AroundServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/around")
@Controller
public class AroundController {

    private final AroundServiceimpl serviceimpl;

    @Autowired
    public AroundController(AroundServiceimpl serviceimpl) {
        this.serviceimpl = serviceimpl;
    }

    @PostMapping("/view")
    @ResponseBody
    public Map<String, Object> aroundView(RequestVO vo) throws Exception{
        return serviceimpl.aroundView(vo);
    }

    @PostMapping("/one")
    @ResponseBody
    public Map<String, Object> aroundOne(RequestVO vo) throws Exception{
        return serviceimpl.aroundOne(vo);
    }

    @PostMapping("/parking")
    @ResponseBody
    public Map<String, Object> parkingList(RequestVO vo) throws Exception{
        return serviceimpl.parkingList(vo);
    }

    @PostMapping("/parking/one")
    @ResponseBody
    public Map<String, Object> parkingOne(RequestVO vo) throws Exception{
        return serviceimpl.parkingOne(vo);
    }

    @PostMapping("/stamp/list")
    @ResponseBody
    public Map<String, Object> stampTourList(RequestVO vo) throws Exception{
        return serviceimpl.stampTourList(vo);
    }

    @PostMapping("/stamp/one")
    @ResponseBody
    public Map<String, Object> stampOne(RequestVO vo) throws Exception{
        return serviceimpl.stampOne(vo);
    }

    @PostMapping("/post/list")
    @ResponseBody
    public Map<String, Object> postingViewList(RequestVO vo) throws Exception{
        return serviceimpl.postingViewList(vo);
    }

    @PostMapping("/post/one")
    @ResponseBody
    public Map<String, Object> postingViewOne(RequestVO vo) throws Exception{
        return serviceimpl.postingViewOne(vo);
    }

    @PostMapping("/post/like")
    @ResponseBody
    public Map<String, Object> postLike(RequestVO vo) throws Exception{
        return  serviceimpl.postLike(vo);
    }

    @PostMapping("/post/mark")
    @ResponseBody
    public Map<String, Object> postBookmark(RequestVO vo) throws Exception{
        return  serviceimpl.postBookmark(vo);
    }

    @PostMapping("/post/write")
    @ResponseBody
    public Map<String, Object> postInsert(MultipartHttpServletRequest request, RequestVO vo) throws Exception{
        return  serviceimpl.postInsert(request,vo);
    }

    @PostMapping("/post/update")
    @ResponseBody
    public Map<String, Object> postUpdate(MultipartHttpServletRequest request, RequestVO vo) throws Exception{
        return  serviceimpl.postUpdate(request,vo);
    }

    @PostMapping("/post/delete")
    @ResponseBody
    public Map<String, Object> postDelete(RequestVO vo) throws Exception{
        return  serviceimpl.postDelete(vo);
    }

    @PostMapping("/post/report")
    @ResponseBody
    public Map<String, Object> reportPost(RequestVO vo) throws Exception{
        return serviceimpl.reportPost(vo);
    }


}
