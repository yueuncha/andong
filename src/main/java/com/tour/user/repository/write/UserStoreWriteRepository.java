package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreWriteRepository {
    int storeView(String str_idx);
    int storeLike(Map<String, Object> params);
    int storeDisLike(Map<String, Object> params);
    int likeCntUp(Map<String, Object> params);
    int likeCntDown(Map<String, Object> params);
    int strReviewCreate(Map<String, Object> params);
    int reviewImageInsert(Map<String, Object> params);
}
