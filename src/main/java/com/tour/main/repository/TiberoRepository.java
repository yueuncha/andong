package com.tour.main.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TiberoRepository {

    List<Map<String, Object>> placeList();

}
