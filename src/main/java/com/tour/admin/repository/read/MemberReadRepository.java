package com.tour.admin.repository.read;

import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberReadRepository {

    List<Map<String, String>> managerList();
    Map<String, Object> managerOne(int mb_idx);
}
