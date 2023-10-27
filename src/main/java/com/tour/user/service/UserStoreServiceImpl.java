package com.tour.user.service;

import com.tour.AES128;
import com.tour.user.repository.read.UserStoreReadRepository;
import com.tour.user.repository.write.UserStoreWriteRepository;
import com.tour.user.service.origin.StoreService;
import com.tour.user.vo.RequestVO;
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
public class UserStoreServiceImpl implements StoreService {
    @Value("#{address['ip']}")
    private String ip;

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final UserStoreReadRepository readRepository;
    private final UserStoreWriteRepository writeRepository;

    @Autowired
    public UserStoreServiceImpl(UserStoreReadRepository readRepository, UserStoreWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Override
    public JSONObject storeList() {
        return null;
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
    public Map<String, Object> bestKeyword(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            List<String> keywordList = readRepository.bestKeyword(newParams);
            ObjectMapper objectMapper = new ObjectMapper();
            if((boolean)oldParams.get("cryption")){
                oldParams.put("data", Encrypt(objectMapper.writeValueAsString(keywordList)));
            }else{
                oldParams.put("data",keywordList);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> storeSearch(RequestVO vo) throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url",ip+"/image/place/");
            if(newParams.containsKey("keyword") && newParams.get("keyword") != ""){
                if(newParams.containsKey("lang") && newParams.get("lang") != ""){
                    writeRepository.searchKeyword(newParams);
                }else{
                    newParams.put("lang", "ko");
                    writeRepository.searchKeyword(newParams);
                }
                List<Map<String, Object>> arr = readRepository.storeSearch(newParams);
                paramRes.put("search", arr);
                paramRes.put("tot_cnt", arr.size());

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                }else{
                    oldParams.put("data",paramRes);
                }

            }else{
                paramRes.put("tot_cnt", 0);
                paramRes.put("search", "");
                oldParams.put("data", paramRes);
                oldParams.replace("result", false);
                oldParams.put("msg", "검색어");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }



    @Override
    public Map<String, Object> categoryList(RequestVO vo) throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url",ip+"/image/place/");

            if(newParams.containsKey("ct_parent")){

                List<Map<String, Object>> arr = readRepository.categoryList(newParams);
                paramRes.put("tot_cnt", arr.size());
                paramRes.put("category", readRepository.categoryName(newParams));
                paramRes.put("store", arr);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                }else{
                    oldParams.put("data",paramRes);
                }
            }else{
                paramRes.put("tot_cnt", "0");
                paramRes.put("category", new JSONObject());
                paramRes.put("store", new JSONObject());

                oldParams.put("data", "");
                oldParams.replace("result", false);
                oldParams.put("msg", "파라미터 확인");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    //String str_category, String lang, String mb_idx
    @Override
    public Map<String, Object> categoryDetail(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");

            if(newParams.containsKey("str_category")){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> temp = readRepository.categoryDetail(newParams);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(temp)));
                }else{
                    oldParams.put("data",temp);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "str_category 확인");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    //String str_idx, String lang, String mb_idx
    @Override
    public Map<String, Object> storeDetail(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("str_idx")){
                newParams.put("url", ip+"/image/place/");
                writeRepository.storeChrt(newParams);
                writeRepository.storeView(String.valueOf(newParams.get("str_idx")));
                List<Map<String, Object>> temp = readRepository.storeDetail(newParams);
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new ObjectMapper().writeValueAsString(temp)));
                }else{
                    oldParams.put("data", temp);
                }
            }else{
                JSONObject json = new JSONObject();
                json.put("review", "");
                json.put("store", "");

                oldParams.put("data", json);
                oldParams.replace("result", false);
                oldParams.put("msg", "str_idx 확인");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    //string str_idx, String mb_idx
    @Override
    public Map<String, Object> storeView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");

            if(newParams.containsKey("str_idx")){
                if((boolean)oldParams.get("cryption")){
                    paramRes.put("data",Encrypt(writeRepository.storeView(String.valueOf(newParams.get("str_idx")))+"") );
                }else{
                    paramRes.put("data",writeRepository.storeView(String.valueOf(newParams.get("str_idx"))));
                }
            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "str_idx 확인");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    //mb_idx, str_idx, like_state
    @Override
    public Map<String, Object> storeLike(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");

            if(newParams.containsKey("mb_idx") && newParams.containsKey("str_idx")){
                boolean temp = false;

                if(writeRepository.storeDisLike(newParams) != 0){
                     newParams.put("cnt", (String.valueOf(newParams.get("like_status"))).equals("Y") ? "+1" : "-1");
                    writeRepository.likeCntUp(newParams);
                }else{
                    newParams.put("cnt", "+1");
                    writeRepository.storeLike(newParams);
                    writeRepository.likeCntUp(newParams);
                }

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
                }else{
                    oldParams.put("data",newParams);
                }

            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "mb_idx, str_idx 확인");
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
                list.add(saveFileName);
            }else{
                System.out.println(saveFile.getPath());
                mFile.transferTo(saveFile);
                list.add(fileName);
            }
        }
        return list;
    }



    //mb_idx, str_idx, str_type, rv_content
    @Override
    public Map<String, Object> strReviewCreate(MultipartHttpServletRequest multipart, RequestVO vo)  throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();
        boolean res = false;

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");

            if (newParams.containsKey("mb_idx") && newParams.containsKey("str_idx") && newParams.containsKey("rv_contents")) {
                res = (writeRepository.strReviewCreate(newParams) != 0) ? true : false;

                //"D:\\image\\review\\"
                //"/data/file/image/"
                List<String> image = imageUpload(multipart.getFiles("files"), "/usr/local/tomcat9/webapps/data/file/image/review/");
                Map<String , Object> imgParams = new HashMap<>();
                imgParams.put("rv_idx", newParams.get("rv_idx"));

                for(String img : image){
                    imgParams.put("rv_img_file", img);
                    writeRepository.reviewImageInsert(imgParams);
                }
                newParams.remove("url");
                newParams.put("create", res);
                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
                } else {
                    oldParams.put("data", newParams);
                }
            } else {
                oldParams.replace("result", false);
                oldParams.put("msg", "파라미터 확인");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> categoryNameList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        oldParams.replace("result", true);
        if(state){
            List<Map<String, Object>> temp = readRepository.categoryNameList();
            JSONArray arr = new JSONArray();

            for(Map<String, Object> map : temp){
                arr.add(new JSONObject(map));
            }

            if ((boolean) oldParams.get("cryption")) {
                oldParams.put("data", Encrypt(arr.toJSONString()));
            } else {
                oldParams.put("data", arr);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", "파라미터 확인");
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> categoryName(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");

            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");
            if(newParams.containsKey("ct_parent")){

                List<Map<String, Object>> temp = readRepository.categoryName(newParams);
                JSONArray arr = new JSONArray();


                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(arr.toJSONString()));
                } else {
                    oldParams.replace("data", arr);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "파라미터 확인");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> experienceList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("ct_parent", 7);
            newParams.put("url", ip+"/image/place/");

            List<Map<String, Object>> arrList = readRepository.experienceList(newParams);
            paramRes.put("tot_cnt", arrList.size());
            paramRes.put("category", readRepository.categoryName(newParams));
            paramRes.put("experience", arrList);

            if ((boolean) oldParams.get("cryption")) {
                oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
            } else {
                oldParams.replace("data", paramRes);
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> mainExperience(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("count", 7);
            newParams.put("random", true);
            newParams.put("url", ip+"/image/place/");


            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> arrList = readRepository.experienceList(newParams);

            if ((boolean) oldParams.get("cryption")) {
                oldParams.put("data", Encrypt(objectMapper.writeValueAsString(arrList)));
            } else {
                oldParams.replace("data", arrList);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> experienceDetail(RequestVO vo) throws Exception {
            Map<String, Object> newParams;
            String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
            Map<String, Object> oldParams = stringToJson(str);
            Map<String, Object> paramRes = new HashMap<>();

            boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                    ? (boolean) oldParams.get("state") : false;

            if(state) {
                newParams = (Map<String, Object>) oldParams.get("result");
                oldParams.replace("result", true);

                if(newParams.containsKey("str_category") && newParams.get("str_category") != ""){
                    newParams.put("url", ip+"/image/place/");
                    List<Map<String, Object>> temp = readRepository.categoryDetail(newParams);
                    paramRes.put("tot_cnt", temp.size());
                    paramRes.put("experience", temp);

                    if ((boolean) oldParams.get("cryption")) {
                        oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                    } else {
                        oldParams.replace("data", paramRes);
                    }

                }else{
                    paramRes.put("tot_cnt", 0);
                    paramRes.put("experience", new JSONObject());
                    oldParams.replace("data", paramRes);
                    oldParams.replace("result", false);
                    oldParams.put("msg", "파라미터 확인");
                }

            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", oldParams);
            }
        return oldParams;
    }

    @Override
    public Map<String, Object> experienceView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");

            if(newParams.containsKey("str_idx")){
                writeRepository.storeChrt(newParams);
                writeRepository.storeView(String.valueOf(newParams.get("str_idx")));
                List<Map<String, Object>> temp = readRepository.storeDetail(newParams);
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new ObjectMapper().writeValueAsString(temp)));
                }else{
                    oldParams.put("data", temp);
                }
            }else{
                oldParams.replace("result", false);
                oldParams.put("msg", "str_idx 확인");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }


    @Override
    public Map<String, Object> festivalList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");
            newParams.put("str_category", 3);

            List<Map<String, Object>> arr = readRepository.categoryDetail(newParams);
            paramRes.put("tot_cnt", arr.size());
            paramRes.put("festival", arr);

            if ((boolean) oldParams.get("cryption")) {
                oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
            } else {
                oldParams.put("data", paramRes);
            }

        } else {
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> festivalView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip+"/image/place/");
            if(newParams.containsKey("str_idx")){
                writeRepository.storeChrt(newParams);
                writeRepository.storeView(String.valueOf(newParams.get("str_idx")));
                ObjectMapper objectMapper = new ObjectMapper();

                List<Map<String, Object>> temp = readRepository.storeDetail(newParams);
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(temp)));
                }else{
                    oldParams.put("data", temp);
                }
            }else{
                oldParams.put("data", "");
                oldParams.replace("result", false);
                oldParams.put("msg", "str_idx 확인");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    public Map<String, Object> chartList(RequestVO vo) throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.get("chrt_gender") == "A"){
                newParams.replace("chrt_gender", "");
            }else if(newParams.get("chrt_age_group") == "0"){
                newParams.replace("chrt_age_group", "");
            }

            newParams.put("count", 7);
            List<Map<String, Object>> chart = readRepository.chartList(newParams);
            ObjectMapper objectMapper = new ObjectMapper();

            if ((boolean) oldParams.get("cryption")) {
                oldParams.put("data", Encrypt(objectMapper.writeValueAsString(chart)));
            } else {
                oldParams.put("data", chart);
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> reviewList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/review/");

            if(newParams.containsKey("str_idx") && newParams.containsKey("mb_idx")){
                List<Map<String, Object>> reviewList = readRepository.strReviewRead(newParams);

                for (Map<String, Object> review: reviewList) {
                    review.put("url", ip + "/image/review/");
                    List<String> images_id = new ArrayList<>();
                    List<String> images = new ArrayList<>();

                    for (Map<String, Object> image :readRepository.reviewImageList(review)) {
                        images.add(String.valueOf(image.get("rv_img_file")));
                        images_id.add(String.valueOf(image.get("rv_img_idx")));
                    }

                    review.put("images", images);
                    review.put("images_id", images_id);

                    review.remove("url");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(reviewList)));
                } else {
                    oldParams.put("data", reviewList);
                }
            }else{
                oldParams.put("msg", "파라미터");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> reviewUpdate(MultipartHttpServletRequest multipart, RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/review/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                    && newParams.containsKey("rv_idx") && newParams.get("rv_idx") != ""){
                writeRepository.rvImageDelete(newParams);
                //D:\\images\\review\\
                ///usr/local/tomcat9/webapps/data/file/image/review/
                List<MultipartFile> files = multipart.getFiles("files");

                if(files.size() != 0 ){
                    List<String> imageFile = imageUpload(files, "/usr/local/tomcat9/webapps/data/file/image/review/" );
                    for (String file: imageFile) {
                        newParams.put("rv_img_file", file);
                        writeRepository.reviewImageInsert(newParams);
                    }
                }

                ObjectMapper objectMapper = new ObjectMapper();
                String image_num = String.valueOf(newParams.get("images"));
                String [] arr = image_num.split(",");

                boolean res = (writeRepository.strReviewUpdate(newParams) != 0) ?  true : false;

                for (String images: arr) {
                    if(res){
                        newParams.put("rv_img_idx", images);
                        writeRepository.rvImageUpdate(newParams);
                    }
                }

                paramRes.put("update", res);

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                } else {
                    oldParams.put("data", paramRes);
                }

            }else{
                oldParams.put("msg", "파라미터");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> reviewDelete(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/review/");
            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                    && newParams.containsKey("rv_idx") && newParams.get("rv_idx") != ""){

                ObjectMapper objectMapper = new ObjectMapper();
                boolean res = (writeRepository.strReviewDelete(newParams) != 0) ?  true : false;
                List<Map<String, Object>> list = readRepository.reviewImageList(newParams);

                for (Map<String, Object> images: list) {
                    if(res && !list.isEmpty()){
                        newParams.put("rv_img_file", images.get("rv_img_file"));
                        writeRepository.rvImageDelete(newParams);
                    }
                }
                paramRes.put("update", res);

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                } else {
                    oldParams.put("data", paramRes);
                }
            }else{
                oldParams.put("msg", "파라미터");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> rvImageDelete(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/review/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""
                    && newParams.containsKey("rv_idx") && newParams.get("rv_idx") != ""){

                ObjectMapper objectMapper = new ObjectMapper();
                paramRes.put("delete", (writeRepository.rvImageDelete(newParams) != 0) ? true : false);

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                } else {
                    oldParams.put("data", paramRes);
                }
            }else{
                oldParams.put("msg", "파라미터");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> menuList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/menu/");

            if(newParams.containsKey("str_idx") && newParams.get("str_idx") != ""){
                List<Map<String, Object>> list = readRepository.menuList(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                /*for (Map<String, Object> params: list) {
                    params.put("menu_images",readRepository.menuImageList(String.valueOf(params.get("mn_idx"))));
                }*/

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(list)));
                } else {
                    oldParams.put("data", list);
                }

            }else{
                oldParams.put("msg", "파라미터");
            }

        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }

    @Override
    public Map<String, Object> menuOne(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(newParams.containsKey("mn_idx") && newParams.get("mn_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                paramRes = readRepository.menuOne(newParams);

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
    public Map<String, Object> festivalBanner(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if (state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/place/");

            if(true){
                List<Map<String, Object>> list = readRepository.festivalBanner(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                if ((boolean) oldParams.get("cryption")) {
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(list)));
                } else {
                    oldParams.put("data", list);
                }
            }else{
                oldParams.put("msg", "파라미터");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }


/*************************************************************************************/

    @Override
    public Map<String, Object> storyTextSave(String values, String fileName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("values",values);
        String [] str = fileName.split("/");
        params.put("fileName",str[str.length]);
        writeRepository.storyTextSave(params);
        return null;
    }
}




