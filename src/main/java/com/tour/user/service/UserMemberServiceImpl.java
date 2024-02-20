package com.tour.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.AES128;
import com.tour.user.repository.read.UserMemberReadRepository;
import com.tour.user.repository.write.UserMemberWriteRepository;
import com.tour.user.service.origin.MemberService;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SocketUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class UserMemberServiceImpl implements MemberService {

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final UserMemberReadRepository readRepository;
    private final UserMemberWriteRepository writeRepository;

    @Autowired
    public UserMemberServiceImpl(UserMemberReadRepository readRepository, UserMemberWriteRepository writeRepository) {
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

    ///mysql
    public String Decrypt(String params) throws Exception{
        AES128 aes = new AES128(key, cryptkey);
        return aes.mySqlDecrypt(params);
    }

    public String Encrypt(String params) throws Exception{
        AES128 aes = new AES128(key, cryptkey);
        return aes.javaEncrypt(params);
    }

    public String mySqlEncrypt(String params) throws Exception{
        AES128 aes = new AES128(key, cryptkey);
        return aes.mySqlEncrypt(params);
    }


    /**
     * 입력 받은 컬럼명 확인
     * */
    public String sqlParamChk(String mb_param) {
        try {
            mb_param = new RequestVO().getClass().getDeclaredField(mb_param).getName();
        } catch (NoSuchFieldException e) {
            mb_param = null;
        }
        return mb_param;
    }

    @Override
    public List<Map<String, Object>> userAgreement(Map<String, Object> param){
        return readRepository.agreementOne(param);
    }

    @Override
    public Map<String, Object> test(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramsRes = new HashMap<>();
        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if((boolean) oldParams.get("cryption")){
                paramsRes.put("ereq", Encrypt(new JSONObject(newParams).toJSONString()));
                paramsRes.put("req", newParams);
            }else{
                paramsRes.put("req", newParams);
                paramsRes.put("ereq", Encrypt(new JSONObject(newParams).toJSONString()));
            }
        }else{
            paramsRes.replace("result", false);
            paramsRes.put("msg", oldParams);
        }

        return paramsRes;
    }

    /**
     * 전체 회원 조회
     * */
    @Override
    public Map<String, Object> userList() throws Exception{
        Map<String , Object> result = new HashMap<>();
        List<Map<String, Object>> lis = readRepository.selectUserList();

        for (Map<String, Object> params: lis) {
            params.replace("mb_email", Decrypt(String.valueOf(params.get("mb_email"))));
            params.replace("mb_pw", Decrypt(String.valueOf(params.get("mb_pw"))));
        }

        result.put("result", lis);
        return result;
    }


    /**
     * 특정 회원 조회
     * */
    @Override
    public Map<String, Object> userOne(RequestVO vo) throws Exception {
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();

        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        int mb_idx = 0;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            mb_idx = (newParams.containsKey("mb_idx")) ? Integer.parseInt(String.valueOf(newParams.get("mb_idx"))) : 0;

            newParams.put("cryptkey", cryptkey);
            paramRes = readRepository.selectUserOne(newParams);
            oldParams.replace("result", true);

            paramRes.replace("mb_email", Decrypt(String.valueOf(paramRes.get("mb_email"))));
            paramRes.replace("mb_pw", Decrypt(String.valueOf(paramRes.get("mb_pw"))));

            if((boolean)oldParams.get("cryption")){
                oldParams.put("data", new JSONObject(paramRes));
            }else{
                oldParams.put("data", paramRes);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    /**
     * 회원 로그인
     * */
    @Override
    public Map<String, Object> userLogin( RequestVO vo) throws Exception {
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            newParams.put("cryptkey", cryptkey);
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_email") && newParams.get("mb_email") != "" && newParams.containsKey("mb_pw") && newParams.get("mb_pw") != ""){
                String email = (newParams.containsKey("mb_email") &&  newParams.get("mb_email") != "") ? mySqlEncrypt(newParams.get("mb_email").toString()): "";
                Map<String, Object> login = (email != null) ? readRepository.userLogin(email, cryptkey) : null;
                System.out.println("login : "+ login);

                boolean loginResult = false;

                if(login != null){
                    login.replace("mb_email", Decrypt(String.valueOf(login.get("mb_email"))));
                    login.replace("mb_pw", Decrypt(String.valueOf(login.get("mb_pw"))));
                    loginResult = (login.get("mb_pw") == String.valueOf(newParams.get("mb_pw"))
                            || !login.isEmpty()) && login != null;
                }


                if(loginResult){
                    oldParams.put("login", true);

                    if((boolean)oldParams.get("cryption") ) {
                        JSONObject json = new JSONObject(login);
                        oldParams.put("data", Encrypt(json.toJSONString()));
                    }else{
                        oldParams.put("data", login);
                    }

                }else{
                    oldParams.put("msg", "아이디/비밀번호를 확인해주세요");
                    oldParams.put("login", false);
                }


                System.out.println("user param" + newParams);
                System.out.println("login result" + oldParams.get("login"));


            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "이메일 / 비밀번호 입력 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    /**
     * 중복체크
     * @oaran
     * mb_param : 중복체크 대상의 컬럼명
     * mb_value : 중복체크 대상의 값
     * */
    @Override
    public Map<String, Object> userDupChk(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            int resNum = 0;
            newParams = (Map<String, Object>) oldParams.get("result");
            String mb_param = (newParams.containsKey("mb_param")) ? newParams.get("mb_param").toString() : null;
            String mb_value = (mb_param != null && newParams.containsKey("mb_value"))
                    ? newParams.get("mb_value").toString() : null;

            oldParams.replace("result", true);

            resNum = (mb_param != null) ? Optional.ofNullable(readRepository.userDupChk(mb_param, mb_value)).orElseGet(() -> 0) : 0;
            String res = (mb_param != null) ? ((resNum != 0 ) ? "Y" : "N") : "파라미터 확인";
            newParams.put("use" , res);

            if((boolean)oldParams.get("cryption")){
                oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
            }else{
                oldParams.put("data", newParams);
            }

        }else {
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    /**
     * 특정 컬럼 값 가져오기
     * */
    @Override
    public Map<String, Object> userDataOne(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");


            oldParams.replace("result", true);

            String mb_param = (newParams.containsKey("mb_param")) ? String.valueOf(newParams.get("mb_param")) : null;
            int mb_idx = (mb_param != null && newParams.containsKey("mb_idx"))
                    ? Integer.parseInt(String.valueOf(newParams.get("mb_idx"))) : 0;

            newParams = (mb_param != null) ? Optional.ofNullable(readRepository.userDataOne(mb_param, mb_idx)).orElseGet(null) : null;
            newParams = (newParams != null) ? newParams : Collections.singletonMap( mb_param, "파라미터 값(컬럼명) 확인");

            if((boolean)oldParams.get("cryption")){
                JSONObject json = new JSONObject(newParams);
                oldParams.put("data", Encrypt(json.toJSONString()));
            }else{
                oldParams.put("data", newParams);
            }
        }else {
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    public Map<String, Object> dupChk(Map<String, Object> params){
        int nickname = readRepository.userDupChk("mb_nickname", String.valueOf(params.get("mb_nickname")));
        int email = readRepository.userDupChk("mb_email", String.valueOf(params.get("mb_nickname")));

        if(nickname != 0 && email != 0){
            params.put("check", false);
            params.put("msg", "이메일, 닉네임 중복");

        }else if(nickname != 0 && email == 0){
            params.put("check", false);
            params.put("msg", "닉네임 중복");
        }else if(email != 0 && nickname == 0){
            params.put("check", false);
            params.put("msg", "이메일 중복");
        }else{
            params.put("check", true);
        }

        return params;
    }

    /**
     * 회원가입
     * @param vo MemberVO
     * */
    @Override
    public Map<String,Object> userJoin(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            newParams = (Map<String, Object>) oldParams.get("result");
            int dupNum = 0;
            oldParams.replace("result", true);


            if(newParams.containsKey("mb_email") && newParams.containsKey("mb_pw")  &&
                    newParams.containsKey("mb_gender") && newParams.containsKey("mb_birth") && newParams.containsKey("mb_nickname") &&
                    newParams.containsKey("mb_local") && newParams.containsKey("mb_local2") && newParams.containsKey("mb_foreigner") &&
                    newParams.containsKey("fcm_token") && newParams.containsKey("app_ver") && newParams.containsKey("app_type") && newParams.containsKey("device_name") && newParams.containsKey("device_os_ver")){

                System.out.println("param : "+  newParams);

                Map<String, Object> chk = dupChk(newParams);
                newParams.put("cryptkey", cryptkey);
                if((boolean)chk.get("check")){
                    boolean res = false;
                    writeRepository.userJoin(newParams);

                    if(Integer.parseInt(String.valueOf(newParams.get("mb_idx"))) != 0){
                        res = writeRepository.fcmInsert(newParams) != 0;
                    }

                    newParams = readRepository.selectUserOne(newParams);

                    System.out.println("user one : "+  newParams);

                    if(newParams.containsKey("mb_email") && newParams.containsKey("mb_pw")){
                        newParams.replace("mb_email", Decrypt(String.valueOf(newParams.get("mb_email"))));
                        newParams.replace("mb_pw", Decrypt(String.valueOf(newParams.get("mb_pw"))));
                    }

                    System.out.println("res : "+res);

                    if((boolean)oldParams.get("cryption")){
                        newParams.put("insert" , res);
                        oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
                    }else{
                        newParams.put("insert" , res);
                        oldParams.put("data", newParams);
                    }
                }else{
                    oldParams.put("result", false);
                    oldParams.put("msg", chk.get("msg"));
                }
            }else{
                oldParams.put("result", false);
                oldParams.put("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.put("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> mailSend(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        boolean emailCheckResult = false;

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        Random random = new Random();
        int resNum = random.nextInt(888888)+111111;
//<p><br></p><p>안동 관광 앱 인증번호 발송</p><p>인증번호 :&nbsp;</p>
        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("email_check", resNum);

            if(newParams.containsKey("fcm_idx") && newParams.get("fcm_idx") != "" && newParams.containsKey("mb_email")
                && newParams.get("mb_email") != ""){

                final String user = "andongsmarttourservice@gmail.com";
                final String password = "ncbmhtwctczhubfg";

                Properties prop = new Properties();
                prop.put("mail.debug", "true");
                prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", 465);
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.ssl.enable", "true");
                prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");


                Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(user));

                    //수신자메일주소
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(newParams.get("mb_email"))));

                    // Subject
                    message.setSubject("[안동관광앱] 인증번호 발송"); //메일 제목을 입력

                    // Text
                    message.setContent("<p><br></p><p>안동 관광 앱 인증번호 발송</p><p>인증번호: " + resNum + "</p>", "text/html; charset=utf-8");

                    // send the message
                    Transport.send(message); ////전송
                    System.out.println("message sent successfully...");
                    emailCheckResult = true;
                } catch (AddressException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    System.out.println("메시지 전송 실패: " + e.getMessage());
                }


                /*
                try {
                    MimeMessage message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(from)); // from
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // recipients
                    message.setSubject("[안동관광앱]인증번호 발송"); // subject
                    message.setText("<p><br></p><p>안동 관광 앱 인증번호 발송</p><p>인증번호 :&nbsp;</p>"); // content

                    Transport.send(message); // send

                    System.out.println("Sent message successfully");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }*/

                boolean res = writeRepository.updateEmailChk(newParams) != 0 && emailCheckResult;
                newParams.put("update", res);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
                }else{
                    oldParams.put("data", newParams);
                }

            }else{
                newParams.put("update", false);
            }



        }else {
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);

        }

        return oldParams;
     }

    @Override
    public Map<String, Object> passwordChange(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();
        boolean temp =false;
        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            int mb_idx = (newParams.containsKey("mb_idx")) ? Integer.parseInt(String.valueOf(newParams.get("mb_idx"))) : 0;
            String mb_pw = (newParams.containsKey("mb_pw") && mb_idx != 0) ? newParams.get("mb_pw").toString() : null;

            oldParams.replace("result", true);

            if(mb_pw != null) {
                newParams.replace("mb_pw", mySqlEncrypt(String.valueOf(newParams.get("mb_pw"))));
                temp = writeRepository.passwordChange(newParams) != 0;
                newParams.put("update", temp);


                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", new JSONObject(newParams).toJSONString());
                }else{
                    oldParams.put("data", newParams);
                }

            }else{
                oldParams.replace("result", false);
                oldParams.put("data", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }


    @Override
    public Map<String, Object> localCategory(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            String mb_foreign = (newParams.containsKey("mb_foreign")) ? newParams.get("mb_foreign").toString() : null;
            oldParams.replace("result", true);

            if(mb_foreign != null){
                List<Map<String, Object>> temp = readRepository.localCategory(mb_foreign);
                JSONArray arr = new JSONArray();

                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(arr.toJSONString()));
                }else{
                    oldParams.put("data", arr);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> localChoice(RequestVO vo) throws Exception{
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> newParams = new HashMap<>();
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams =(Map<String, Object>) oldParams.get("result");
            String set_1_code = (newParams.containsKey("set_1_code")) ? newParams.get("set_1_code").toString() : null;

            oldParams.replace("result", true);

            if(set_1_code != null){
                List<Map<String, Object>> temp = readRepository.localChoice(set_1_code);
                JSONArray arr = new JSONArray();

                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }
                if((boolean) oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(arr.toJSONString()));
                }else{
                    oldParams.put("data", arr);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.replace("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.replace("msg", oldParams);
        }

        return oldParams;
    }

    // mb_idx , app_ver, app_type , device_name , fcm_token , device_os_ver
    @Override
    public Map<String, Object> sessionChk(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){

            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("user_lat") && newParams.containsKey("user_lng")){
                if(newParams.get("user_lat").equals("")){
                    newParams.replace("user_lat", 0);
                    newParams.replace("user_lng", 0);
                }
            }
                boolean res = false;

                if(!newParams.get("fcm_idx").equals("")){
                    // 기존회원 or 게스트
                    if(newParams.get("fcm_idx").equals("")){
                       res = !readRepository.sessionData(newParams).isEmpty();
                    }else if((newParams.get("mb_idx").equals("") || Integer.parseInt(String.valueOf(newParams.get("mb_idx"))) == 0) && newParams.get("mb_idx").equals("")){
                        newParams.put("mb_idx", 0);
                        res = writeRepository.sessionUpdate(newParams) != 0;
                    }else{
                        res = writeRepository.sessionUpdate(newParams) != 0;
                    }

                }else if((newParams.get("fcm_idx").equals("") || newParams.get("fcm_idx").equals(null)) &&  newParams.get("mb_idx").equals("")){
                    // 신규 게스트
                    if(newParams.get("mb_idx").equals("")){
                        newParams.put("mb_idx", 0);
                        newParams.put("fcm_idx", 0);
                    }
                    res = writeRepository.fcmInsert(newParams) != 0;

                }else {
                    oldParams.replace("result", false);
                    oldParams.put("msg", res);
                }

                Map<String, Object> result = readRepository.sessionData(newParams);
                System.out.println("session result : "+ result);
                if((boolean) oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(result).toJSONString()));

                }else{
                    oldParams.put("data",  result);
                }


        }else {
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }


    @Override
    public Map<String, Object> newSession(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");
        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            //(newParams.containsKey("mb_idx")) ? newParams.get("mb_idx") : 0;
            if(newParams.containsKey("fcm_idx") && newParams.get("fcm_idx") != ""){
               //session update

            }else{
                // session insert
            }

            if((boolean) oldParams.get("cryption")){
                oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
            }else{
                oldParams.put("data",  newParams);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return null;
    }

    //
    @Override
    public Map<String, Object> memberEmailChk(RequestVO vo) throws Exception {

        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            if(newParams.containsKey("fcm_idx") && newParams.get("fcm_idx") != "" && newParams.containsKey("email_check")){
                newParams.put("mb_idx" , 0);
                Map<String, Object> temp = readRepository.sessionData(newParams);
                if(temp.get("email_check") != null){

                    int db_email = Integer.parseInt(String.valueOf(temp.get("email_check")));
                    int param_email = Integer.parseInt(String.valueOf(newParams.get("email_check")));

                    temp.put("check", db_email == param_email);
                }else{
                    temp.put("check", false);
                }

                if((boolean) oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(temp).toJSONString()));
                }else{
                    oldParams.put("data",  temp);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.replace("msg", "파라미터");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.replace("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> alarmCheck(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("alarm_type") && newParams.get("alarm_type") != ""
                    && newParams.containsKey("fcm_idx") && newParams.get("fcm_idx") != ""){

                // P : 푸시 M : 마케팅

                if(newParams.get("alarm_type").equals("P")){
                   newParams.put("type", "push_use");
                }else{
                    newParams.put("type", "marketing_use");
                }

                boolean res = writeRepository.alarmCheck(newParams) != 0;
                paramRes.put("update", res);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                }else{
                    oldParams.put("data", paramRes);
                }

            }else{
                oldParams.put("result", false);
                oldParams.put("msg", " 파라미터 확인 ");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> snsUserLogin(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);


            if(newParams.containsKey("mb_ci") && !newParams.get("mb_ci").equals("") && newParams.containsKey("mb_sns") ){
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(newParams);

                Map<String, Object> login = readRepository.snsUserLogin(newParams);

                System.out.println(login);

                if(Integer.parseInt(String.valueOf(login.get("login_result"))) != 0){
                    login.replace("login_result", true);
                }else{
                    login.replace("login_result", false);
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(login)));
                }else{
                    oldParams.put("data", login);
                }

            }else{
                oldParams.put("result", false);
                oldParams.put("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> userPushList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("fcm_idx") && newParams.get("fcm_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> list = readRepository.userPushList(newParams);
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(list)));
                }else{
                    oldParams.put("data", list);
                }

            }else{
                oldParams.put("result", false);
                oldParams.put("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }
}