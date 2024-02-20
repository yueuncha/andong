package com.tour.user.service;

import com.tour.AES128;
import com.tour.main.service.FCMService;
import com.tour.user.vo.RequestVO;
import com.tour.user.repository.read.AroundReadRepository;
import com.tour.user.repository.write.AroundWriteRepository;
import com.tour.user.service.origin.AroundService;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.*;

@Service
public class AroundServiceimpl implements AroundService {

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{address['ip']}")
    private String ip;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final AroundReadRepository readRepository;
    private final AroundWriteRepository writeRepository;
    private final FCMService fcmService;

    @Autowired
    public AroundServiceimpl(AroundReadRepository readRepository, AroundWriteRepository writeRepository, FCMService fcmService) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
        this.fcmService = fcmService;
    }

    public Map<String, Object> stringToJson(String str) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(str);

        if(json.containsKey("cryption") && (boolean)json.get("cryption")){
            AES128 aes = new AES128(key, cryptkey);
            String result = String.valueOf(json.get("result"));
            JSONObject temp = (JSONObject) parser.parse(aes.javaDecrypt(result));
            json.replace("result", temp);
        }else if(json.containsKey("cryption") && !(boolean)json.get("cryption")){
            String result = String.valueOf(json.get("result"));
            JSONObject temp = (JSONObject) parser.parse(result);
            json.replace("result", temp);
        }

        return json;
    }

    public String Encrypt(String params) throws Exception{
        AES128 aes = new AES128(key, cryptkey);
        return aes.javaEncrypt(params);
    }

    public String Decrypt(String params) throws Exception{
        AES128 aes = new AES128(key, cryptkey);
        return aes.javaDecrypt(params);
    }

    @Override
    public Map<String, Object> aroundView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("ct_idx") && newParams.get("ct_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                paramRes.put("category", readRepository.aroundCategory(newParams));
                paramRes.put("list", readRepository.aroundView(newParams));

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                }else{
                    oldParams.put("data", paramRes);
                }

            }else{
                paramRes.put("category", "");
                paramRes.put("list", "");
                oldParams.put("data", paramRes);
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
    public Map<String, Object> aroundOne(RequestVO vo) throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("str_idx") && newParams.get("str_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                paramRes = readRepository.aroundOne(newParams);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
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
    public Map<String, Object> parkingList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("parking_free_check")){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> list = readRepository.parkingList(newParams);

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

    @Override
    public Map<String, Object> parkingOne(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("parking_mng_code") && newParams.get("parking_mng_code") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> one = readRepository.parkingOne(newParams);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(one)));
                }else{
                    oldParams.put("data", one);
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
    public Map<String, Object> stampTourList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("storeUrl", ip + "/image/place/");
            newParams.put("iconUrl", ip + "/image/stamp/android/");

            System.out.println("stamp : "+ newParams);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String,Object>> list = readRepository.stempTourList(newParams);

                paramRes.put("list", list);
                paramRes.put("tot_count", readRepository.stempTotCount(newParams));
                paramRes.put("y_stamp_count", readRepository.stempYesCount(newParams));

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                }else{
                    oldParams.put("data", paramRes);
                }

            }else{
                paramRes.put("list", "");
                paramRes.put("tot_stamp", 0);
                paramRes.put("y_stamp_count", 0);
                oldParams.put("data", paramRes);
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
    public Map<String, Object> stampOne(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("storeUrl", ip + "/image/place/");
            newParams.put("iconUrl", ip + "/image/stamp/android/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                paramRes =readRepository.stampOne(newParams);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                }else{
                    oldParams.put("data",  paramRes);
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
    public Map<String, Object> postingViewList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");

            if(newParams.containsKey("page")){
                int page = (Integer.parseInt(String.valueOf(newParams.get("page")))-1 ) * 10;
                newParams.replace("page", page);
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(newParams);
                List<Map<String, Object>> list = readRepository.postingViewList(newParams);

                for (Map<String, Object> values : list) {
                    values.replace("images", Arrays.asList(String.valueOf(values.get("images")).split(",")));
                }

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

    @Override
    public Map<String, Object> postingViewOne(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");

            if(newParams.containsKey("post_idx") && !newParams.get("post_idx").equals("")){
                ObjectMapper objectMapper = new ObjectMapper();
                paramRes = readRepository.postViewOne(newParams);

                if(paramRes.containsKey("images") && paramRes.containsKey("images_id")
                    && !paramRes.get("images").equals("") && !paramRes.get("images_id").equals("")){
                    paramRes.replace("images",Arrays.asList(String.valueOf(paramRes.get("images")).split(",")));
                    paramRes.replace("images_id",Arrays.asList(String.valueOf(paramRes.get("images_id")).split(",")));
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
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
    public Map<String, Object> postingViewInsert(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(null)));
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

    public Map<String, Object> postLike(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.containsKey("post_idx")){
                boolean temp = false;

                if(writeRepository.postDisLike(newParams) != 0){
                    newParams.put("cnt", (String.valueOf(newParams.get("like_status"))).equals("Y") ? "+1" : "-1");
                    writeRepository.likeCntUp(newParams);
                }else{
                    newParams.put("cnt", (String.valueOf(newParams.get("like_status"))).equals("Y") ? "+1" : "-1");
                    writeRepository.postLike(newParams);
                    writeRepository.likeCntUp(newParams);
                }

                Map<String, Object> result = readRepository.postViewOne(newParams);
                newParams.put("push_type", "like");

                Map<String, Object> fcmParams = readRepository.pushFCM(newParams);
                System.out.println(fcmParams);
                if(!fcmParams.isEmpty()){
                    if(fcmParams.get("push_use").equals("Y")){
                        fcmService.pushFCM(fcmParams);
                    }
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(result).toJSONString()));
                }else{
                    oldParams.put("data",result);
                }

            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> postBookmark(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                && newParams.containsKey("post_idx") && newParams.get("post_idx") != ""
                    && newParams.containsKey("mark_now") && newParams.get("mark_now") != ""){
                if(newParams.get("mark_now").equals("Y")){
                    newParams.replace("mark_now", "N");
                }else{
                    newParams.replace("mark_now", "Y");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                writeRepository.postBookmark(newParams);

                newParams.put("push_type", "mark");

                Map<String, Object> fcmParams = readRepository.pushFCM(newParams);

                System.out.println(fcmParams);

                if(!fcmParams.isEmpty()){
                    if(fcmParams.get("push_use").equals("Y")){
                        fcmService.pushFCM(fcmParams);
                    }
                }


                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
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

    public List<String> imageUpload(List<MultipartFile> files, String folderName) throws Exception{
        //MultipartFile mFile;
        List<String> list = new ArrayList<>();
        String saveFileName = "", saveFilePath = "";

        for (MultipartFile mFile: files) {
            String fileName = mFile.getOriginalFilename();
            list.add(fileName);
            String fileCutName = fileName.substring(0, fileName.lastIndexOf("."));
            String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
            saveFilePath = folderName+ File.separator + fileName;

            File fileFolder = new File(folderName);

            if(!fileFolder.exists()){
                if(fileFolder.mkdirs()){
                    System.out.println("file mkdirs : SUCCESS");
                }else{
                    System.out.println("file mkdirs : FAIL");
                }
            }

            File saveFile = new File(saveFilePath);
            if(saveFile.isFile()){
                boolean _exist = true;

                int index = 0;

                while(_exist){
                    index ++;

                    saveFileName = fileCutName + "(" + index + ")." + fileExt;
                    String dictFile = folderName + File.separator + saveFileName;
                    _exist = new File(dictFile).isFile();
                    if(!_exist){
                        saveFilePath = dictFile;
                    }
                }
                mFile.transferTo(new File(saveFilePath));
            }else{
                System.out.println(saveFile.getPath());
                mFile.transferTo(saveFile);
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> postInsert(MultipartHttpServletRequest request, RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();//
                List<String> image = imageUpload(request.getFiles("files"), "usr/local/tomcat9/webapps/data/file/image/mypage/");
                boolean res = writeRepository.postInsert(newParams) != 0;

                for(String img : image){
                    newParams.put("my_image_file", img);
                    writeRepository.postImageSave(newParams);
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("upload",res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("upload",res));
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
    public Map<String, Object> postUpdate(MultipartHttpServletRequest multipart,RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();


        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                && newParams.containsKey("post_idx") && newParams.get("post_idx") != ""){
                writeRepository.postImageDeleteAll(newParams);
                //D:\\images\\review\\
                ///usr/local/tomcat9/webapps/data/file/image/mypage/
                if(multipart != null){
                    List<MultipartFile> files = multipart.getFiles("files");
                    if(files.size() != 0 ){
                        List<String> imageFile = imageUpload(files, "/usr/local/tomcat9/webapps/data/file/image/mypage/" );
                        for (String file: imageFile) {
                            newParams.put("my_image_file", file);
                            writeRepository.postImageSave(newParams);
                        }
                    }
                }

                ObjectMapper objectMapper = new ObjectMapper();
                String image_num = String.valueOf(newParams.get("images"));
                String [] arr = image_num.split(",");

                boolean res = writeRepository.postUpdate(newParams) != 0;

                if(arr.length != 0){
                    for (String images: arr) {
                        if(res){
                            newParams.put("my_idx", images);
                            writeRepository.postImageUpdate(newParams);
                        }
                    }
                }

                paramRes.put("update", res);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
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
    public Map<String, Object> postDelete(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                    && newParams.containsKey("post_idx") && newParams.get("post_idx") != ""){

                ObjectMapper objectMapper = new ObjectMapper();
                boolean res = writeRepository.postDelete(newParams) != 0;

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("report", res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("report", res));
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
    public Map<String, Object> reportPost(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                boolean res = writeRepository.reportPost(newParams) != 0;

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("report", res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("report", res));
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
    public Map<String, Object> stampGiveCheck(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption") && (boolean) oldParams.get("state");

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                Map<String, Object> check_value = readRepository.stampGiveCheck(newParams);
                if(check_value.get("mb_give_status").equals("UPDATE")){
                    int insert_count = Integer.parseInt(String.valueOf(check_value.get("user_code_count")))
                            - Integer.parseInt(String.valueOf(check_value.get("give_now_code")));

                    System.out.println("insert_count : " + insert_count);

                    for (int i = 0; i < insert_count; i++) {
                        newParams.put("give_number", new Random().nextInt(888888)+111111);
                        if(writeRepository.stampCodeInsert(newParams) == 0){
                            System.out.println(newParams);
                        }
                    }
                }
                List<Map<String, Object>> give_list = readRepository.stampGiveList(newParams);
                check_value.put("list", give_list);

                ObjectMapper objectMapper = new ObjectMapper();

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(check_value)));
                }else{
                    oldParams.put("data", check_value);
                }

                System.out.println(check_value);

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
    public Map<String, Object> stampInformation() throws Exception {
        Map<String, Object> oldParams = stringToJson(new RequestVO().getReq());

        if(true) {
            oldParams.replace("result", true);
            oldParams.put("data", readRepository.stampBoardView());
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }
}

