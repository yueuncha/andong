package com.tour.user.service.socket;

import com.tour.main.service.FCMService;
import com.tour.user.repository.read.AroundReadRepository;
import com.tour.user.repository.write.AroundWriteRepository;
import com.tour.user.service.AroundServiceimpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.*;


@Component
@ServerEndpoint(value = "/ws/stamp" , configurator = SocketEndPointConfig.class)
public class SocketHandler extends TextWebSocketHandler {

    private static final Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    private final AroundReadRepository readRepository;
    private final AroundWriteRepository writeRepository;
    private final FCMService fcmService;

    @Autowired
    public SocketHandler(AroundReadRepository readRepository, AroundWriteRepository writeRepository, FCMService fcmService) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
        this.fcmService = fcmService;
    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println("hi client : " + session);

        if(!clients.contains(session)){
            clients.add(session);
        } else {

        }
    }

    @OnMessage
    public void onMessage(Session session, String msg){
        System.out.println("receive message : " + msg);
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(msg);
            Map<String, Object> stamp = readRepository.userStampCalc(json);

        for(Session s : clients) {
            try {
                if(!stamp.isEmpty()){
                    boolean userCheck = Integer.parseInt(String.valueOf(stamp.get("mb_idx"))) != 0;
                    stamp.put("userCheck", userCheck);

                    if(userCheck){
                        if(String.valueOf(stamp.get("stamp_status")).equals("N")){
                            boolean insertResult = writeRepository.stampInsert(stamp) != 0;
                            if(insertResult){
                                /// 스탬프 획득
                                if(!stamp.get("token").equals("") || stamp.get("token") != null){
                                    s.getBasicRemote().sendText(Collections.singletonMap("fcm_send_result", fcmService.stampFCM(stamp)).toString());
                                }
                            }
                        }
                    }else if(userCheck){
                        /// @@@에서 스탬프를 획득할 수 있습니다. 로그인하고 더 많은 스탬프와 경품을 획득하세요
                        if(!stamp.get("token").equals("") || stamp.get("token") != null){
                            s.getBasicRemote().sendText(Collections.singletonMap("fcm_send_result", fcmService.stampFCM(stamp)).toString());
                        }
                    }
                }else{

                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("bye client : " + session);
        clients.remove(session);
    }

}