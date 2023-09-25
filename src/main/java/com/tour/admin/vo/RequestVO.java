package com.tour.admin.vo;

import lombok.Data;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class RequestVO {

    private String req;
    private String ereq;

    public String getReq(){
        Map<String, Object> result = new HashMap<>();
        if(req != null){
            result.put("data", req);
            result.put("msg", "");
            result.put("cryption", false);
            result.put("state", (req != null) ? true : false);
        }else{
            result = null;
        }
        return new JSONObject(result).toJSONString();
    }

    public String getEreq(){
        Map<String, Object> result = new HashMap<>();
        if(req != null){
            result.put("data", ereq);
            result.put("msg", "");
            result.put("cryption", false);
            result.put("state", (req != null) ? true : false);
        }else{
            result = null;
        }
        return new JSONObject(result).toJSONString();
    }
}
