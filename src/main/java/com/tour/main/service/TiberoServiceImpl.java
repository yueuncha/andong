package com.tour.main.service;

import com.tour.main.repository.TiberoRepository;
import com.tour.main.repository.write.MysqlPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TiberoServiceImpl implements TiberoService{

    private final TiberoRepository tibero;
    private final MysqlPlaceRepository mysql;

    @Autowired
    public TiberoServiceImpl(TiberoRepository tibero, MysqlPlaceRepository mysql) {
        this.tibero = tibero;
        this.mysql = mysql;
    }

    @Override
    public List<Map<String, Object>> placeList() {

        return tibero.placeList();
    }
}
