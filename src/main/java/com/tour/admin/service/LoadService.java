package com.tour.admin.service;

import com.tour.admin.repository.read.LoadReadRepository;
import com.tour.admin.repository.write.LoadWriteRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class LoadService {

    private final LoadReadRepository loadReadRepository;
    private final LoadWriteRepository loadWriteRepository;

    @Autowired
    public LoadService(LoadWriteRepository loadWriteRepository, LoadReadRepository loadReadRepository) {
        this.loadWriteRepository = loadWriteRepository;
        this.loadReadRepository = loadReadRepository;
    }

    public boolean detailInsert() throws Exception{

        List<String> list = loadReadRepository.cotentidSelect();

        for (String contentid: list) {
            String urlStr = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?MobileOS=ETC&MobileApp=andong-tour&_type=json&" +
                    "contentId=" +
                    contentid +
                    "&defaultYN=Y&firstImageYN=Y&addrinfoYN=Y&overviewYN=Y&numOfRows=9999&serviceKey=n0pWWW%2B4YGIcdLVSRvboh%2BCDWiK7X%2B8t335b6p56e%2BOP7KGLpVUjmwbTh1Rme4ZeXCJHEo1Tidrw6DpWmZjQqQ%3D%3D";
            String result = "";
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = br.readLine();

            System.out.println(result);
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(result);
            System.out.println(json);
            if(json.containsKey("response")){
                JSONObject response = (JSONObject) json.get("response");
                JSONObject body = (JSONObject) response.get("body");


                JSONObject items = (JSONObject)body.get("items");
                JSONArray item = (JSONArray) items.get("item");

                for (Object obj: (JSONArray) item) {
                    JSONObject params = (JSONObject)obj;

                    if(!params.isEmpty()){
                        loadWriteRepository.detailInsert(params);
                        System.out.println(params.get("title"));
                    }else{
                        System.out.println("[없음]");
                    }

                }
            }else{
                System.out.println("[없음]");
            }

        }


        return true;
    }


    public boolean storeInsert(JSONArray arr) throws Exception{
        for (Object obj: (JSONArray) arr) {
            JSONObject params = (JSONObject)obj;
            loadWriteRepository.storeInsert(params);
        }

        return true;
    }


}
