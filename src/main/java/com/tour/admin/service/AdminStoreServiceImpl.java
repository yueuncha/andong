package com.tour.admin.service;

import com.tour.admin.repository.read.AdminStoreReadRepository;
import com.tour.admin.repository.write.AdminStoreWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminStoreServiceImpl implements AdminStoreService{

    private final AdminStoreReadRepository readRepository;
    private final AdminStoreWriteRepository writeRepository;

    @Autowired
    public AdminStoreServiceImpl(AdminStoreReadRepository readRepository, AdminStoreWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Override
    public List<Map<String, Object>> adminStoreList(Map<String, Object> params) throws Exception {
        return readRepository.storeListAll(params);
    }

    @Override
    public List<Map<String, Object>> storeViewOne(int str_idx) throws Exception {
        return readRepository.storeViewOne(str_idx);
    }

    @Override
    public List<Map<String, Object>> adminCategoryList(Map<String, Object> params) throws Exception {
        return readRepository.adminCategoryStepList(params);
    }

    @Override
    public Map<String, Object> adminCategoryOne(int ct_idx) throws Exception {

        return readRepository.adminCategoryOne(ct_idx);
    }
}
