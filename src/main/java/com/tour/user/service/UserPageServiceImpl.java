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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPageServiceImpl implements PageService {

    @Value("#{address['ip']}")
    private String ip;

    @Value("#{aesConfig['key']}")
    private String key;

    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    UserPageReadRepository userPageReadRepository;
    UserPageWriteRepository userPageWriteRepository;

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
                newParams.put("count", 3);
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
                    review.put("review_images", userPageReadRepository.reviewImageList(newParams));
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
                newParams.put("count", 0);
                paramRes.put("images", userPageReadRepository.myImageLoad(newParams));

                if((boolean)oldParams.get("cryption")){
                    oldParams.put("data", Encrypt(new JSONObject(paramRes).toJSONString()));
                }else{
                    oldParams.put("data", paramRes);
                }

            }else {
                paramRes.put("images", "");
                paramRes.put("count", "");
                oldParams.put("result", paramRes);
                oldParams.put("msg", " 파라미터 확인 ");
            }
        }else{
            oldParams.replace("result", false);
            oldParams.put("msg", oldParams);
        }
        return oldParams;
    }
}
