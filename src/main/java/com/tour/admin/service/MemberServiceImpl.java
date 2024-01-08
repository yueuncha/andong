package com.tour.admin.service;

import com.tour.AES128;
import com.tour.admin.repository.read.MemberReadRepository;
import com.tour.admin.repository.write.MemberWriteRepository;
import com.tour.user.vo.RequestVO;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService{

    @Value("#{aesConfig['key']}")
    private String key;
    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    @Value("#{address['ip']}")
    private String ip;

    private final MemberReadRepository readRepository;
    private final MemberWriteRepository writeRepository;

    @Autowired
    public MemberServiceImpl(MemberReadRepository readRepository, MemberWriteRepository writeRepository) {
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
            json.replace("data", temp);
        }else{
            String result = String.valueOf(json.get("result"));
            JSONObject temp = (JSONObject) parser.parse(result);
            json.replace("data", temp);
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

    @Override
    public ModelAndView managerView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/managerView");
        mv.addObject("List",readRepository.managerList());
        return mv;
    }

    @Override
    public int managerSave(Map<String, Object> params) {
        try{
            AES128 aes = new AES128(key, cryptkey);
            String password = aes.javaEncrypt(params.get("mb_pw").toString());
            params.replace("mb_pw", password);
        }catch (Exception e){}
        System.out.println(params);
        return writeRepository.managerInsert(params);
    }

    @Override
    public Map<String, Object> managerOne(int mb_idx) {
        return readRepository.managerOne(mb_idx);
    }

    @Override
    public int mailSend(String email) {
        Random random = new Random();
        int resNum = random.nextInt(888888)+111111;

        String contents = "인증번호 :" ;

        //Message message =

        return resNum;
    }

    @Override
    public List<Map<String, Object>> userList(Map<String, Object> params) throws Exception {
        System.out.println(params);
        List<Map<String, Object>> list = readRepository.userList(params);
        for (Map<String, Object> param: list) {
            param.replace("mb_email", Decrypt(String.valueOf(param.get("mb_email"))));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> inquiryList(Map<String, Object> params) throws Exception {
        return readRepository.inquiryList(null);
    }

    @Override
    public Map<String, Object> inquiryView(int inq_idx) throws Exception {
        return  readRepository.inquiryView(inq_idx);
    }

    @Override
    public int inquiryWrite(int iq_idx, String inquiryAnswer) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("iq_idx", iq_idx);
        param.put("inquiry_content", inquiryAnswer);
        System.out.println(param);
        int num = writeRepository.inquiryAnswer(param);
        System.out.println(num);
        return 0;
    }

    @Override
    public List<Map<String, Object>> reportList(Map<String, Object> params) throws Exception {
        return readRepository.reportList();
    }

    @Override
    public Map<String, Object> reportView(int report_idx) throws Exception {
        return  readRepository.reportView(report_idx);
    }

    @Override
    public List<Map<String, Object>> faqList() throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> faqView(int faq_idx) throws Exception {
        return null;
    }

    @Override
    public int faqWrite(Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public int faqUpdate(Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public int faqDelete(int faq_idx) throws Exception {
        return 0;
    }
}
