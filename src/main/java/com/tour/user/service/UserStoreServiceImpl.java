package com.tour.user.service;

import com.tour.user.repository.read.UserStoreReadRepository;
import com.tour.user.repository.write.UserStoreWriteRepository;
import com.tour.user.service.origin.MemberService;
import com.tour.user.service.origin.StoreService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStoreServiceImpl implements StoreService {

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

    @Override
    public List<Map<String, Object>> storeSearch(Map<String, Object> params) {
        String keyword = (params.get("keyword").toString()).replaceAll(" ", "");
        params.replace("keyword", keyword);
        return readRepository.storeSearch(params);
    }

    @Override
    public Map<String, Object> categoryList(Map<String, Object> params) {
        boolean result = (params != null && !params.isEmpty() ) ?
                (params.get("ct_parent") != null && params.get("ct_parent") != "") ? true : false : false;
        Map<String, Object> map = new HashMap<>();

        if(result){
            map = Collections.singletonMap("result", readRepository.categoryList(params));
        }else{
            map = Collections.singletonMap("result", "상위 카테고리를 선택해주세요");
        }
        
        return map;
    }

    @Override
    public Map<String, Object> categoryDetail(String str_category, String lang, String mb_idx) {
        Map<String, Object> map;

        boolean result = (str_category != null && str_category.equals("")) ? true : false;

        if(result){
            map = Collections.singletonMap("result", readRepository.categoryDetail(str_category, lang, mb_idx)); 
        }else {
            map = Collections.singletonMap("result", "카테고리를 선택해주세요");
        }
        
        return map;
    }

    @Override
    public Map<String, Object> storeDetail(String str_idx, String lang, String mb_idx) {
        Map<String, Object> map;
        boolean result = (str_idx != null && str_idx.equals("")) ? true : false;
        if(result){
            map = Collections.singletonMap("result",readRepository.storeDetail(str_idx, lang, mb_idx));
        }else {
            map = Collections.singletonMap("result","업체idx 확인");
        }
        return map;
    }

    @Override
    public Map<String, Object> storeView(String str_idx, String mb_idx) {
        int sqlRes = (str_idx != "" && str_idx != null ) ? writeRepository.storeView(str_idx) : -1;
        String res = (sqlRes == 0 || sqlRes == -1) ?  (sqlRes == -1)  ?  "str_idx 업체 idx 번호가 없습니다" : "FAIL" : "SUCCESS";
        return Collections.singletonMap("result", res);
    }

    @Override
    public Map<String, Object> storeLike(Map<String, Object> params) {
        String res = "";
        Map<String, Object> map;
        boolean result = (params != null && !params.isEmpty()) ?
                (params.get("mb_idx") != null && params.get("mb_idx").equals("") && params.get("str_idx") != null
                        && params.get("str_idx").equals("")) ? true : false : false;

        if(result){
            if(params.get("like_status").toString().equals("Y")){
                /*업데이트*/
                res = (writeRepository.storeDisLike(params) != 0 && writeRepository.likeCntDown(params) != 0)
                        ? "SUCCESS" : "파라미터(str_idx, mb_idx) 확인";
            }else{
                /*신규등록*/
                res = (writeRepository.storeLike(params) != 0 && writeRepository.likeCntUp(params) != 0 )
                        ? "SUCCESS" : "파라미터(str_idx, mb_idx) 확인";
            }
            map = Collections.singletonMap("result", res);
        }else{
            map = Collections.singletonMap("result", "mb_idx, str_idx 빈값 확인");
        }
        return map;
    }

    @Override
    public Map<String, Object> strReviewCreate(Map<String, Object> params) {
        Map <String, Object> map;
        String res = "";
        boolean result = (!params.isEmpty() && params != null) ?
                (params.get("mb_idx") != null && params.get("mb_idx").equals("") &&
                        params.get("str_idx") != null && params.get("str_idx").equals("")) ? true : false : false;
        if(result){
            res = (writeRepository.strReviewCreate(params) != 0) ? "SUCCESS" : "파라미터 확인";
        }else {
            res = "파라미터 확인";
        }
        return Collections.singletonMap("result", res);
    }


}
