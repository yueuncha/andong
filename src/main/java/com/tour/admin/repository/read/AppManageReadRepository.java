package com.tour.admin.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppManageReadRepository {
    List<Map<String, Object>> andongStoryList();
    Map<String, Object> andongStoryView(int tour_idx);
    List<Map<String, Object>> bannerLoad();
    List<Map<String, Object>> stampList();
    List<Map<String, Object>> stampUserList();
    List<Map<String, Object>> pushSendList(Map<String, Object> params);
    List<Map<String, Object>> recommendTourList();
    List<Map<String, Object>> boardUserList();

}
