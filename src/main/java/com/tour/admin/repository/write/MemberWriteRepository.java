package com.tour.admin.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface MemberWriteRepository {

    int managerInsert(Map<String, Object> params);


}
