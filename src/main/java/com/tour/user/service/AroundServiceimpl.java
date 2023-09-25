package com.tour.user.service;

import com.tour.AES128;
import com.tour.user.vo.RequestVO;
import com.tour.user.repository.read.AroundReadRepository;
import com.tour.user.repository.write.AroundWriteRepository;
import com.tour.user.service.origin.AroundService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public AroundServiceimpl(AroundReadRepository readRepository, AroundWriteRepository writeRepository) {
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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);

            if(true){
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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

        if(state) {
            newParams = (Map<String, Object>) oldParams.get("result");
            oldParams.replace("result", true);
            newParams.put("storeUrl", ip + "/image/place/");
            newParams.put("iconUrl", ip + "/image/stamp/android/");

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

        boolean state = (oldParams != null && oldParams.containsKey("result") && oldParams.containsKey("cryption"))
                ? (boolean) oldParams.get("state") : false;

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
}
