package com.tour.admin.service;

import java.util.List;
import java.util.Map;

public interface AppManageService {
    List<Map<String, Object>> andongStoryList() throws Exception;

    Map<String, Object> andongStoryView(int tour_idx) throws Exception;

    List<Map<String, Object>> bannerLoad() throws Exception;
    boolean bannerLoadAdd() throws Exception;
    int bannerDelete(int bn_idx) throws Exception;

    List<Map<String, Object>> stampList() throws Exception;
    List<Map<String, Object>> stampUserList() throws Exception;

    List<Map<String, Object>> pushSendList();
    List<Map<String, Object>> recommendTourList();

    List<Map<String, Object>> boardUserList();

    int bannerUpdate(Map<String, Object> banner) throws Exception;
}
