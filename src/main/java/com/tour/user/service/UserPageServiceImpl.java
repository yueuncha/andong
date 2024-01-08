package com.tour.user.service;

import com.tour.AES128;
import com.tour.user.repository.read.UserPageReadRepository;
import com.tour.user.repository.write.UserPageWriteRepository;
import com.tour.user.service.origin.PageService;
import com.tour.user.vo.RequestVO;
import org.codehaus.jackson.map.ObjectMapper;
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
public class UserPageServiceImpl implements PageService {

    @Value("#{address['ip']}")
    private String ip;

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final UserPageReadRepository userPageReadRepository;
    private final UserPageWriteRepository userPageWriteRepository;

    @Autowired
    public UserPageServiceImpl(UserPageReadRepository userPageReadRepository, UserPageWriteRepository userPageWriteRepository) {
        this.userPageReadRepository = userPageReadRepository;
        this.userPageWriteRepository = userPageWriteRepository;
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
    public Map<String, Object> myPage(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                paramRes.put("count", userPageReadRepository.myPageCnt(newParams));
                paramRes.put("images", userPageReadRepository.myImageLoad(newParams));

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
    public Map<String, Object> myImageSave(MultipartHttpServletRequest multipart,RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ///usr/local/tomcat9/webapps/data/file/image/mypage/
                //D:\\image\\mypage\\
                List<String> image = imageUpload(multipart.getFiles("files"), "usr/local/tomcat9/webapps/data/file/image/mypage/");
                List<String> imageRes = new ArrayList<>();
                for(String img : image){
                    newParams.put("my_image_file", img);
                    userPageWriteRepository.myImageSave(newParams);
                    imageRes.add(ip+"/image/mypage/"+img);
                }
                newParams.remove("url");
                newParams.put("images", imageRes);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(newParams).toJSONString()));
                }else{
                    oldParams.put("data", newParams);
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
    public Map<String, Object> myLike(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                List<Map<String, Object>> arr = userPageReadRepository.myLike(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(arr)));
                }else{
                    oldParams.put("data", arr);
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
    public Map<String, Object> myReview(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                List<Map<String, Object>> arr = userPageReadRepository.myReview(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                newParams.put("url", ip+"/image/review/");

                for (Map<String, Object> review: arr) {
                    newParams.put("rv_idx", review.get("rv_idx"));
                    List<String> images_id = new ArrayList<>();
                    List<String> images = new ArrayList<>();

                    for (Map<String, Object> image : userPageReadRepository.reviewImageList(newParams)) {
                        images.add(String.valueOf(image.get("rv_img_file")));
                        images_id.add(String.valueOf(image.get("rv_img_idx")));
                    }

                    review.put("images", images);
                    review.put("images_id", images_id);
                }

                newParams.remove("url");
                paramRes.put("review", arr);

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(arr)));
                }else{
                    oldParams.put("data", arr);
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
    public Map<String, Object> imageView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                newParams.put("url", ip+"/image/mypage/");
                //newParams.put("count", 0);
                paramRes.put("images", userPageReadRepository.myImageLoad(newParams));

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                }else{
                    oldParams.put("data", paramRes);
                }

            }else {
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
    public Map<String, Object> myImageDelete(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");
            int count = 0;

            if(newParams.containsKey("images") && newParams.get("images") != ""
                    && newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){

                //boolean deleteRes = (userPageWriteRepository.myImageDeleteAll(newParams) != 0) ? true : false;
                ObjectMapper objectMapper = new ObjectMapper();
                String image_num = String.valueOf(newParams.get("images"));
                String [] arr = image_num.split(",");

                for (String images: arr) {
                    newParams.put("my_idx", images);
                    userPageWriteRepository.myImageDelete(newParams);
                }

                paramRes.put("update", (arr.length - count) +"개 삭제");

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
    public Map<String, Object> bannerView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            //newParams.put("url", ip + "/image/mypage/");

            if(true){
                List<Map<String, Object>> list = userPageReadRepository.bannerList(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

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
    public Map<String, Object> storyList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/story/");

            if(true){
                List<Map<String, Object>> list = userPageReadRepository.storyList(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

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
    public Map<String, Object> storyRandom(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/story/");
            newParams.put("type",  "R");

            if(true){
                List<Map<String, Object>> list = userPageReadRepository.storyRandom(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

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
    public Map<String, Object> storyView(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/story/");

            if(newParams.containsKey("tour_idx") && newParams.get("tour_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                userPageWriteRepository.tourViewCnt(newParams);
                paramRes = userPageReadRepository.storyView(newParams);

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
    public Map<String, Object> passList(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                List<Map<String, Object>> list = userPageReadRepository.passList(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                for (Map<String, Object> params: list) {
                    newParams.put("ps_idx",params.get("ps_idx"));
                    params.put("images", userPageReadRepository.passImages(newParams));
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
    public Map<String, Object> passDayList(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("ps_idx") && newParams.get("ps_idx") != ""){

                List<Map<String, Object>> list = userPageReadRepository.passDayList(newParams);
                ObjectMapper objectMapper = new ObjectMapper();

                if((boolean)oldParams.get("cryption")){
                    if(list.isEmpty()){
                        oldParams.put("data", "여행패스를 추가하세요");
                    }else{
                        oldParams.put("data", Encrypt(objectMapper.writeValueAsString(list)));
                    }
                }else{
                    if(list.isEmpty()){
                        oldParams.put("data", "여행패스를 추가하세요");
                    }else{
                        oldParams.put("data", list);
                    }
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
    public Map<String, Object> passSave(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("ps_title") && newParams.get("ps_title") != ""
                && newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){

                boolean res = (userPageWriteRepository.passInsert(newParams) != 0) ? true  : false;

                if(res){
                    userPageWriteRepository.passDayFirst(Integer.parseInt(String.valueOf(newParams.get("ps_idx"))));
                }

                paramRes.put("write", res);
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
    public Map<String, Object> passDaySave(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/story/");

            if(newParams.containsKey("ps_idx") && newParams.get("ps_idx") != ""){
                boolean res = (userPageWriteRepository.passDayInsert(newParams) != 0) ? true : false;
                paramRes.put("write", res);

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
    public Map<String, Object> passStoreSave(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("ps_idx") && newParams.get("ps_idx") != ""
                    && newParams.containsKey("str_idx") && newParams.get("str_idx") != ""){

                boolean res = (userPageWriteRepository.passStoreInsert(newParams) != 0) ? true : false;
                paramRes.put("write", res);

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
    public Map<String, Object> passDelete(RequestVO vo) throws Exception {
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

            if(true){

                boolean res = (userPageWriteRepository.passDelete(newParams) != 0) ? true : false;
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
    public Map<String, Object> passDayUpdate(RequestVO vo) throws Exception {
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

            if(true){

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(""));
                }else{
                    oldParams.put("data", null);
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
    public Map<String, Object> passDayDelete(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("day_number") && newParams.get("day_number") != ""
                    && newParams.containsKey("ps_idx") && newParams.get("ps_idx") != ""){

                boolean res = (userPageWriteRepository.passDayDelete(newParams) != 0) ? true : false;
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
    public Map<String, Object> passStoreUpdate(RequestVO vo) throws Exception {
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

            if(true){

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(""));
                }else{
                    oldParams.put("data", null);
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
    public Map<String, Object> passStoreDelete(RequestVO vo) throws Exception {
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

            if(newParams.containsKey("str_idx") && newParams.get("str_idx") != ""
                    && newParams.containsKey("day_number") && newParams.get("day_number") != ""
                    && newParams.containsKey("psl_idx") && newParams.get("psl_idx") != ""){

                boolean res = (userPageWriteRepository.passStoreDelete(newParams) != 0) ? true : false;
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
    public Map<String, Object> passNameUpdate(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/story/");

            if(newParams.containsKey("ps_title") && newParams.get("ps_title") != ""
                    && newParams.containsKey("ps_idx") && newParams.get("ps_idx") != ""
                    && newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){

                boolean res = (userPageWriteRepository.passNameUpdate(newParams) != 0) ? true : false;
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
    public Map<String, Object> bestPassList(RequestVO vo) throws Exception {
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

            if(true){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> list = userPageReadRepository.bestPassList(newParams);

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
    public Map<String, Object> questCategory(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> list = userPageReadRepository.inquiryCategory(newParams);
            if((boolean)oldParams.get("cryption")){
                oldParams.put("data", Encrypt(objectMapper.writeValueAsString(list)));
            }else{
                oldParams.put("data", list);
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }

        return oldParams;
    }

    @Override
    public Map<String, Object> questInsert(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();

                int testNumber = userPageWriteRepository.inquiryInsert(newParams);
                boolean res = (testNumber != 0) ? true : false;

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("update", res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("update", res));
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
    public Map<String, Object> questList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();

                List<Map<String, Object>> list = userPageReadRepository.inquiryList(newParams);
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
    public Map<String, Object> questOne(RequestVO vo) throws Exception {

        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("iq_idx") && newParams.get("iq_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();

                List<Map<String, Object>> list = userPageReadRepository.inquiryOne(newParams);
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
    public Map<String, Object> questUpdate(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && !newParams.get("mb_idx").equals("")
                && newParams.containsKey("iq_idx") && !newParams.get("iq_idx").equals("")){

                if(newParams.get("iq_status").equals("E")){
                    ObjectMapper objectMapper = new ObjectMapper();
                    boolean res = (userPageWriteRepository.inquiryUpdate(newParams) != 0) ? true : false;

                    if((boolean)oldParams.get("cryption")){
                        oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("update",res))));
                    }else{
                        oldParams.put("data", Collections.singletonMap("update", res));
                    }
                }else{
                    oldParams.put("result", false);
                    oldParams.put("msg", " 답변이 완료된 게시글입니다. ");
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
    public Map<String, Object> myBookMark(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("url", ip + "/image/mypage/");

            if(newParams.containsKey("mb_idx") && newParams.get("mb_idx") != ""){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> list = userPageReadRepository.myBookMark(newParams);
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
    public Map<String, Object> inquiryDelete(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") && newParams.containsKey("iq_idx")){
                ObjectMapper objectMapper = new ObjectMapper();

                boolean res = (userPageWriteRepository.inquiryDelete(newParams) != 0) ? true : false;
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("update", res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("update", res));
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

    public Map<String, Object> userSecession(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("mb_idx") ){
                ObjectMapper objectMapper = new ObjectMapper();

                boolean res = (userPageWriteRepository.userSecession(newParams) != 0) ? true : false;
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(Collections.singletonMap("update", res))));
                }else{
                    oldParams.put("data", Collections.singletonMap("update", res));
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
    public Map<String, Object> faqList(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(newParams.containsKey("faq_category")){
                ObjectMapper objectMapper = new ObjectMapper();

                paramRes.put("faq", userPageReadRepository.userFaqList(newParams));
                paramRes.put("category", userPageReadRepository.userFaqCategoryList(null));
                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(objectMapper.writeValueAsString(paramRes)));
                }else{
                    oldParams.put("data", Collections.singletonMap("data", paramRes));
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
