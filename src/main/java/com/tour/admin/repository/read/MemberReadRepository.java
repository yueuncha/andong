package com.tour.admin.repository.read;

import com.tour.user.vo.RequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberReadRepository {

    List<Map<String, String>> managerList();
    Map<String, Object> managerOne(int mb_idx);

    List<Map<String, Object>> storeList(Map<String, Object> params);
    Map<String, Object> storeOne(int str_idx);
    List<String> dbCodeList();

}
