package com.tour.admin.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoadWriteRepository {

    int storeInsert(Map<String, Object> p);
    int detailInsert (Map<String, Object> p);
}
