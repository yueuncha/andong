package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserStoreWriteRepository {
    List<Map<String, Object>> storeSearch(Map<String, Object> param);
}
