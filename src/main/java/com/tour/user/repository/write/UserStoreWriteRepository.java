package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreWriteRepository {
    int storeView();
    int storeLike();
    int storeReview();
}
