package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;

import java.util.List;
import java.util.Map;

public interface AroundService {

    Map<String, Object> aroundView(RequestVO vo) throws Exception;
    Map<String, Object> aroundOne(RequestVO vo) throws Exception;
    Map<String, Object> parkingList(RequestVO vo) throws Exception;
    Map<String, Object> parkingOne(RequestVO vo) throws Exception;
    Map<String, Object> stampTourList(RequestVO vo) throws Exception;
    Map<String, Object> stampOne(RequestVO vo) throws Exception;
}
