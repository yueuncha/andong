package com.tour.user.service;

import com.tour.user.repository.read.UserStoreReadRepository;
import com.tour.user.repository.write.UserStoreWriteRepository;
import com.tour.user.service.origin.MemberService;
import com.tour.user.service.origin.StoreService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public JSONObject storeSearch(Map<String, Object> param) {
        String keyword = (param.get("keyword").toString()).replaceAll(" ", "");
        param.replace("keyword", keyword);
        writeRepository.storeSearch(param);
        return null;
    }
}
