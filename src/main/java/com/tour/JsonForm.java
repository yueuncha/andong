package com.tour;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonForm {

    private LocalDateTime time;

    public JsonForm(LocalDateTime time) {
        this.time = time;

    }

    public Map<String, Object> setData(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("localTime",time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        return map;
    }




}
