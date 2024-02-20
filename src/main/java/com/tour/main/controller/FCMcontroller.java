package com.tour.main.controller;

import com.tour.main.service.FCMService;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
public class FCMcontroller {

    private final FCMService service;

    @Autowired
    public FCMcontroller(FCMService service) {
        this.service = service;
    }

    @RequestMapping("/fcm/one")
    @ResponseBody
    public String FcmSendOne(RequestVO vo) throws Exception{
        return service.FcmSendOne(vo);
    }


}
