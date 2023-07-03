package com.tour.main.repository.write;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MysqlPlaceRepository {

    int insertPlace();

}
