package com.tour.user.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AroundReadRepository {
    List<Map<String, Object>> aroundView(Map<String, Object> params);
    List<Map<String, Object>> aroundCategory(Map<String, Object> params);
    Map<String, Object> aroundOne(Map<String, Object> params);
    List<Map<String, Object>> parkingList(Map<String, Object> params);
    List<Map<String, Object>> stempTourList(Map<String, Object> params);
    Map<String, Object> parkingOne(Map<String, Object> params);
    Map<String, Object> stampOne(Map<String, Object> params);
    int stempYesCount(Map<String, Object> params);
    int stempTotCount(Map<String, Object> params);
}
