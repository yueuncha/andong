package com.tour.user.service;

import com.tour.AES128;
import com.tour.user.repository.read.UserStoreReadRepository;
import com.tour.user.repository.write.UserStoreWriteRepository;
import com.tour.user.service.origin.MemberService;
import com.tour.user.service.origin.StoreService;
import com.tour.user.vo.RequestVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStoreServiceImpl implements StoreService {
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
    public List<Map<String, Object>> storeSearch(RequestVO vo) {
       /* String keyword = (params.get("keyword").toString()).replaceAll(" ", "");
        params.replace("keyword", keyword);
        return readRepository.storeSearch(params);*/
        return  null;
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

            if(newParams.containsKey("ct_parent")){
                List<Map<String, Object>> temp = readRepository.categoryList(newParams);
                JSONArray arr = new JSONArray();

                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }

                if((boolean)oldParams.get("cryption")){
                    paramRes.put("tot_cnt", arr.size());
                    paramRes.put("ereq", Encrypt(arr.toJSONString()));
                }else{
                    paramRes.put("tot_cnt", arr.size());
                    paramRes.put("req",arr);
                }
            }else{
                paramRes.put("error", "상위 카테고리를 선택해주세요");
            }

        }else{
            paramRes.put("error", oldParams);
        }
        
        return paramRes;
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

            if(newParams.containsKey("str_category")){
                List<Map<String, Object>> temp = readRepository.categoryDetail(newParams);
                JSONArray arr = new JSONArray();

                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }

                if((boolean)oldParams.get("cryption")){
                    paramRes.put("tot_cnt", arr.size());
                    paramRes.put("ereq", Encrypt(arr.toJSONString()));
                }else{
                    paramRes.put("tot_cnt", arr.size());
                    paramRes.put("req",arr);
                }
            }else{
                paramRes.put("error", "카테고리를 선택해주세요");
            }

        }else{
            paramRes.put("error", oldParams);
        }

        return paramRes;
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
            if(newParams.containsKey("str_idx")){
                writeRepository.storeView(String.valueOf(newParams.get("str_idx")));
                List<Map<String, Object>> temp = readRepository.storeDetail(newParams);
                JSONArray arr = new JSONArray();

                for(Map<String, Object> map : temp){
                    arr.add(new JSONObject(map));
                }
                if((boolean)oldParams.get("cryption")){
                    paramRes.put("ereq", Encrypt(arr.toJSONString()));
                }else{
                    paramRes.put("req", arr);
                }
            }else{
                paramRes.put("error", " 파라미터 확인 ");
            }
        }else{
            paramRes.put("error", oldParams);
        }
        return paramRes;
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

            if(newParams.containsKey("str_idx")){
                if((boolean)oldParams.get("cryption")){
                    paramRes.put("ereq",writeRepository.storeView(String.valueOf(newParams.get("str_idx"))) );
                }else{
                    paramRes.put("req",writeRepository.storeView(String.valueOf(newParams.get("str_idx"))));
                }
            }else{
                paramRes.put("error", " 파라미터 확인 ");
            }

        }else{
            paramRes.put("error", oldParams);
        }
        return paramRes;
    }

    //mb_idx, str_idx, like_state
    @Override
    public Map<String, Object> storeLike(RequestVO vo) throws Exception {
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state){
            newParams = (Map<String, Object>) oldParams.get("result");
            if(newParams.containsKey("mb_idx") && newParams.containsKey("str_idx")){
                String temp = "";
                if((String.valueOf(newParams.get("like_status"))).equals("Y")){
                    /*업데이트*/
                    newParams.replace("like_status", "N");
                    temp = (writeRepository.storeDisLike(newParams) != 0 && writeRepository.likeCntDown(newParams) != 0)
                            ? "SUCCESS" : "파라미터(str_idx, mb_idx) 확인";
                }else{
                    /*신규등록*/
                    newParams.replace("like_status", "Y");
                    temp = (writeRepository.storeLike(newParams) != 0 && writeRepository.likeCntUp(newParams) != 0 )
                            ? "SUCCESS" : "파라미터(str_idx, mb_idx) 확인";

                }

                newParams.put("result", temp);

                if((boolean)oldParams.get("cryption")){
                    paramRes.put("ereq", Encrypt(new JSONObject(newParams).toJSONString()));
                }else{
                    paramRes.put("req",newParams);
                }
            }else{
                paramRes.put("error", " 파라미터 확인 ");
            }
        }else{
            paramRes.put("error", oldParams);
        }

        return paramRes;
    }

    //mb_idx, str_idx, str_type, rv_content
    @Override
    public Map<String, Object> strReviewCreate(RequestVO vo)  throws Exception{
        Map<String, Object> newParams;
        String str = (vo.getReq() != null) ? vo.getReq() : vo.getEreq();
        Map<String, Object> oldParams = stringToJson(str);
        Map<String, Object> paramRes = new HashMap<>();

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            if (newParams.containsKey("mb_idx") && newParams.containsKey("str_idx") && newParams.containsKey("rv_contents")) {
                if ((boolean) oldParams.get("cryption")) {
                    paramRes.put("ereq", (writeRepository.strReviewCreate(newParams) != 0) ? true : false);
                } else {
                    paramRes.put("req", (writeRepository.strReviewCreate(newParams) != 0) ? true : false);
                }
            } else {
                paramRes.put("error", " 파라미터 확인 ");
            }
        }else{
            paramRes.put("error", oldParams);
        }

        return paramRes;
    }
}
