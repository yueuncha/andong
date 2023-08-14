package com.tour.user.service;

import com.tour.user.repository.read.UserStoreReadRepository;
import com.tour.user.repository.write.UserStoreWriteRepository;
import com.tour.user.service.origin.MemberService;
import com.tour.user.service.origin.StoreService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public List<Map<String, Object>> categoryList(String ct_parent, String lang) {
        return readRepository.categoryList(ct_parent, lang);
    }

    @Override
    public List<Map<String, Object>> categoryDetail(String str_category, String lang) {
        return readRepository.categoryDetail(str_category, lang);
    }

    @Override
    public List<Map<String, Object>> storeDetail(String str_idx, String lang) {
        return readRepository.storeDetail(str_idx, lang);
    }

    @Override
    public Map<String, Object> storeView(String str_idx) {
        String res = (writeRepository.storeView(str_idx) != 0) ? "SUCCESS" : "FAIL";
        return Collections.singletonMap("result", res);
    }

    @Override
    public Map<String, Object> storeLike(Map<String, Object> params) {
        String like_res = params.get("like_status").toString();
        String res = "";
        System.out.println(like_res);
        if(like_res.equals("Y")){

            /*업데이트*/
            writeRepository.storeDisLike(params);
            writeRepository.likeCntDown(params);

        }else{
            /*신규등록*/
            writeRepository.storeLike(params);
            writeRepository.likeCntUp(params);
        }

        return Collections.singletonMap("result", res);
    }

    @Override
    public JSONObject storeReview() {
        return null;
    }


}
