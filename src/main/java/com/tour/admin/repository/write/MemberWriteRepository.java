package com.tour.admin.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface MemberWriteRepository {

    int managerInsert(Map<String, Object> params);

    int jusoMapping(Map<String, Object> params);

    int kkebiInsert(Map<String, Object> params);

    int menuInsert(Map<String, Object> params);
    int detailInsert(Map<String, Object> params);
    int Updatecontents(Map<String, Object> params);

    /***********************************************************/

    int inquiryAnswer(Map<String, Object> params);




}
