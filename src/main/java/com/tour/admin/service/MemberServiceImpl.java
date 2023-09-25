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
    public ModelAndView storeList() throws Exception {

        Map<String, Object> newParams;
        Map<String, Object> oldParams = new HashMap<>();
        ModelAndView mv = new ModelAndView();

        newParams = new HashMap<>();
        newParams.put("url", ip+"/image/place/");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = readRepository.storeList(newParams);

        mv.addObject("data", list);
        mv.addObject("code", readRepository.dbCodeList());
        oldParams.put("data", list);

        return mv;
    }

    @Override
    public Map<String, Object> storeOne() throws Exception{
        List<String> codeList = readRepository.dbCodeList();
        String file_name = "";
        ArrayList<String> files = new ArrayList<>();
        int cnt = 0;

        for (int i = 0; i<codeList.size(); i++) {
            String result = "";
            String urlStr = "https://mukkebi.com/app/mk082_new_business_view.php?sp_min_delivery_tip=0&sp_update=2023-08-22 00:58:04&coupon=y&lat=37.239334&tab=&bis_time=y&event_yn=Y&lng=131.869812&sp_idx="+codeList.get(i)+"&new_version=17&sp_max_delivery_tip=0";
            urlStr = urlStr.replaceAll(" ","");

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "multipart/file");

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = br.readLine();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);

            Map<String, Object> detail = new HashMap<>();

            detail.put("str_dt_name", json.get("sp_name"));
            detail.put("str_dt_address", json.get("sp_addr1"));
            detail.put("str_dt_desc", json.get("sp_origin"));
            detail.put("str_idx", json.get("sp_idx"));

            writeRepository.detailInsert(detail);
            if(json.containsKey("menu_list")){
                JSONArray menuList = (JSONArray) json.get("menu_list");

                if(!menuList.isEmpty()){
                    for (int j = 0; j <menuList.size(); j++) {
                        JSONObject obj = (JSONObject) menuList.get(j);
                        Map<String, Object> temp = new HashMap<>();
                        temp.put("str_idx", obj.get("sp_idx"));
                        if(obj.containsKey("menu")){
                            JSONArray objItems = (JSONArray) obj.get("menu");
                            for (int k = 0; k < objItems.size(); k++) {
                                if(!objItems.isEmpty()){
                                    JSONObject item = (JSONObject) objItems.get(k);
                                    if(item.containsKey("sm_img") && !item.get("sm_img").equals("") && item.get("sm_img") != null){
                                         temp.put("mn_nm", item.get("sm_name"));
                                        int firstIdx = String.valueOf(item.get("sm_img")).lastIndexOf("/") + 1;
                                        int lastIdx = String.valueOf(item.get("sm_img")).length();
                                        file_name = String.valueOf(item.get("sm_img")).substring(firstIdx, lastIdx);
                                        temp.put("mn_image", file_name);
                                    }
                                     writeRepository.Updatecontents(temp);
                                }
                            }
                        }

                    }
                }
            }

            
            
                    //= (JSONArray) json.get("list");

            /*JSONObject[] objs = new JSONObject[arr.size()];
            for (int j = 0; j < arr.size(); j++) {
                objs[j] = (JSONObject) arr.get(j);
            }*/

            /*for (JSONObject jsonResult : objs) {
                Map<String, Object> temp = new HashMap<>();
                if (jsonResult.containsKey("sp_idx") && jsonResult.get("sp_idx") != "" && jsonResult.get("sp_idx") != null) {
                    int firstIdx = String.valueOf(jsonResult.get("f_name1")).lastIndexOf("/") + 1;
                    int lastIdx = String.valueOf(jsonResult.get("f_name1")).length();
                    file_name = String.valueOf(jsonResult.get("f_name1")).substring(firstIdx, lastIdx);
                    //files.add(String.valueOf(jsonResult.get("f_name1")));
                    *//*temp.put("str_idx", jsonResult.get("sp_idx"));
                    int firstIdx = String.valueOf(jsonResult.get("f_name1")).lastIndexOf("/") + 1;
                    int lastIdx = String.valueOf(jsonResult.get("f_name1")).length();
                    file_name = String.valueOf(jsonResult.get("f_name1")).substring(firstIdx, lastIdx);
                    System.out.println(file_name);
                    temp.put("str_image", file_name);
                    temp.put("str_category", jsonResult.get("sp_category"));
                    temp.put("str_lat", jsonResult.get("sp_y_pos"));
                    temp.put("str_lng", jsonResult.get("sp_x_pos"));
                    temp.put("operating_hour", jsonResult.get("operating_hour"));
                    temp.put("str_phone", jsonResult.get("sp_tel"));*//*
                }
               // writeRepository.kkebiInsert(temp);
            }*/
        }

        //System.out.println(files.size());
        //imageUpload(files);



        return null;
    }

    public void imageUpload(ArrayList<String> fileName) throws Exception{
        ///usr/local/tomcat9/webapps/data/file/image/place/
        String folderName = "/usr/local/tomcat9/webapps/data/file/image/menu/";
        File folder = new File(folderName);

        for (String file: fileName) {
            if(file != "" && file != null ){

                if(file.contains("https://")){
                    URL url = new URL(file);
                    String saveFileName = "", saveFilePath = "";

                    BufferedImage image = ImageIO.read(url);

                    int firstIdx = file.lastIndexOf("/") + 1;
                    int lastIdx = file.length();

                    String fileCutName = file.substring(firstIdx, file.lastIndexOf("."));
                    String fileExt = file.substring(file.lastIndexOf(".")+1);

                    saveFileName = file.substring(firstIdx, lastIdx);

                    if(!folder.exists()){
                        if(folder.mkdirs()){
                            System.out.println("file mkdirs : SUCCESS");
                        }else {
                            System.out.println("file mkdirs : FAIL");
                        }
                    }

                    File saveFile = new File(folderName+saveFileName);

                    if(saveFile.isFile()) {
                        boolean _exist = true;

                        int index = 0;

                        while (_exist) {
                            index++;

                            saveFileName = fileCutName + "(" + index + ")." + fileExt;
                            String dictFile = folderName + File.separator + saveFileName;
                            _exist = new File(dictFile).isFile();
                            if (!_exist) {
                                saveFilePath = dictFile;
                            }
                        }
                        ImageIO.write(image, fileExt, new File(saveFilePath));
                    }else{
                        ImageIO.write(image, fileExt, saveFile);
                    }
                }


            }
        }
    }

    @Override
    public Map<String, Object>  jusoMapping(Map<String, Object> params) throws Exception {
        System.out.println(params);
        writeRepository.jusoMapping(params);
        return null;
    }
}
