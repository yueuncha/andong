package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserPageWriteRepository {
    int myImageSave(Map<String, Object> params);
}
