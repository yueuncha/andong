package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserPageWriteRepository {
    int myImageSave(Map<String, Object> params);

    int tourViewCnt(Map<String, Object> params);

    int passInsert(Map<String, Object> params);

    int passDayFirst(int ps_idx);

    int passDayInsert(Map<String, Object> params);

    int passStoreInsert(Map<String, Object> params);

    int myImageDelete(Map<String, Object> params);

    int myImageDeleteAll(Map<String, Object> params);

    int passStoreDelete(Map<String, Object> params);

    int passDayDelete(Map<String, Object> params);

    int passDelete(Map<String, Object> params);

    int passNameUpdate(Map<String, Object> params);
}
