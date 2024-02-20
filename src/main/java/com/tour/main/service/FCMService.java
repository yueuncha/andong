package com.tour.main.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.tour.AES128;
import com.tour.user.repository.read.AroundReadRepository;
import com.tour.user.repository.write.AroundWriteRepository;
import com.tour.user.vo.RequestVO;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class FCMService {

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final AroundReadRepository readRepository;
    private final AroundWriteRepository writeRepository;

    @Autowired
    public FCMService(AroundReadRepository readRepository, AroundWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    public Map<String, Object> stringToJson(String str) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(str);

        if(json.containsKey("cryption") && (boolean)json.get("cryption")){
            AES128 aes = new AES128(key, cryptkey);
            String result = String.valueOf(json.get("result"));
            JSONObject temp = (JSONObject) parser.parse(aes.javaDecrypt(result));
            json.replace("result", temp);
        }else{
            String result = String.valueOf(json.get("result"));
            JSONObject temp = (JSONObject) parser.parse(result);
            json.replace("result", temp);
        }

        return json;
    }

    public FirebaseApp firebaseAppInit() throws Exception{
        InputStream refreshToken = new FileInputStream("/usr/local/tomcat9/webapps/data/file/config/andongtour-smart-firebase-adminsdk-26f2c-5b8174dfd8.json");

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if(firebaseAppList != null && !firebaseAppList.isEmpty()){
            for (FirebaseApp app: firebaseAppList) {
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    firebaseApp = app;
                }
            }
        }else{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return firebaseApp;
    }



    public String FcmSendOne(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        String response = "FAIL";

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            Map<String, Object> newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            FirebaseApp app = firebaseAppInit();

            Message message = Message.builder()
                    .putData("title", String.valueOf(newParams.get("title")))
                    .putData("content", String.valueOf(newParams.get("content")))
                    .setToken(String.valueOf(newParams.get("token")))
                    .build();

            try {
                response = FirebaseMessaging.getInstance(app).send(message);
                System.out.println("Successfully sent message: " + response);

            } catch (Exception e) {
                e.printStackTrace();
                response = "FAIL";
            }

        }
        return response;
    }



    public boolean stampFCM(Map<String, Object> params) throws Exception{
        boolean result = false;
        String response = "";
        FirebaseApp app = firebaseAppInit();

        if(!(boolean)params.get("userCheck")){
            params.replace("content", params.get("str_dt_name")+"에서 스탬프를 획득할 수 있습니다! 로그인하고 더 많은 스탬프와 경품을 획득하세요.");
        }

        System.out.println(params);

        Message message = Message.builder()
                .setToken(String.valueOf(params.get("token")))
                .setNotification(
                        Notification.builder()
                                .setTitle(String.valueOf(params.get("title")))
                                .setBody(String.valueOf(params.get("content")))
                                .build()
                )
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setTitle(String.valueOf(params.get("title")))
                                                .setBody(String.valueOf(params.get("content")))
                                                //.setClickAction("push_click")
                                                .build()
                                )
                                .build()
                )
                .setApnsConfig(
                        ApnsConfig.builder()
                                .setAps(Aps.builder()
                                        //.setCategory("push_click")
                                        .build())
                                .build()
                )
                .putData("type",String.valueOf(params.get("type")))
                .build();

        try {
            response = FirebaseMessaging.getInstance(app).send(message);
            System.out.println("Successfully sent message: " + response);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        System.out.println(result);

        if(result){
            result = writeRepository.fcmLogInsert(params) != 0;
        }
        return result;
    }

    public boolean pushFCM(Map<String, Object> params) throws Exception{
        boolean result = false;
        String response = "";
        FirebaseApp app = firebaseAppInit();

        Message message = Message.builder()
                .setToken(String.valueOf(params.get("token")))
                .setNotification(
                        Notification.builder()
                                .setTitle(String.valueOf(params.get("title")))
                                .setBody(String.valueOf(params.get("content")))
                                .build()
                )
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setTitle(String.valueOf(params.get("title")))
                                                .setBody(String.valueOf(params.get("content")))
                                                //.setClickAction("push_click")
                                                .build()
                                )
                                .build()
                )
                .setApnsConfig(
                        ApnsConfig.builder()
                                .setAps(Aps.builder()
                                        //.setCategory("push_click")
                                        .build())
                                .build()
                )
                .putData("type",String.valueOf(params.get("type")))
                .build();

        try {
            response = FirebaseMessaging.getInstance(app).send(message);

            System.out.println("Successfully sent message: " + response);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        if(result){
            result = writeRepository.fcmLogInsert(params) != 0;
        }
        return result;
    }



}
