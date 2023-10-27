package com.tour.user.vo;

import com.tour.AES128;
import lombok.Data;
import org.apache.hadoop.hdfs.web.JsonUtilClient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Data
public class RequestVO {

    public String key;
    private String req;
    private String ereq;

    public String getReq(){
        String res = null;
        Map<String, Object> result = new HashMap<>();
        if(req != null){
            result.put("result", req);
            result.put("data", "");
            result.put("msg", "");
            result.put("cryption", false);
            result.put("state", (req != null) ? true : false);
            res = new JSONObject(result).toJSONString();
        }else{
            res = result.toString();
        }
        return res;
    }

    public String getEreq() {
        Map<String, Object> result = new HashMap<>();
        String res = null;
        if(ereq != null){
           result.put("result", ereq);
           result.put("data", "");
            result.put("msg", "");
           result.put("cryption", true);
           result.put("state", (ereq != null) ? true : false);
           res = new JSONObject(result).toJSONString();
       }else{
           res = null;
       }

        return res;
    }
}
