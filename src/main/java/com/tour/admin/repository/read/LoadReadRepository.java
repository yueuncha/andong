package com.tour.admin.repository.read;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoadReadRepository {

    List<String> cotentidSelect();
}
