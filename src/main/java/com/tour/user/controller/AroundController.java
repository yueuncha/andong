package com.tour.user.controller;

import com.tour.user.vo.RequestVO;
import com.tour.user.service.AroundServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
