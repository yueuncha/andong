package com.tour.user.repository.write;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AroundWriteRepository {
    int postLike(Map<String, Object> params);
    int likeCntUp(Map<String, Object> params);
    int likeCntDown(Map<String, Object> params);
    int postDisLike(Map<String, Object> newParams);
    int postBookmark(Map<String, Object> params);
    int postInsert(Map<String, Object> params);
    int postImageSave(Map<String, Object> params);
    int postImageDeleteAll(Map<String, Object> params);
    int postUpdate(Map<String, Object> params);
    int postImageUpdate(Map<String, Object> params);
    int stampInsert(Map<String, Object> params);
    int reportPost (Map<String, Object> params);
    int postDelete(Map<String, Object> params);
    int fcmLogInsert(Map<String, Object> params);

    int stampCodeInsert(Map<String, Object> params);
}
