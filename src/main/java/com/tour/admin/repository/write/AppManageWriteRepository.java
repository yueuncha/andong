package com.tour.admin.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AppManageWriteRepository {
    void bannerLoadAdd(Map<String, Object> param);
    int bannerDelete(int bn_idx);
    int bannerUpdate(Map<String, Object> banner);
}
