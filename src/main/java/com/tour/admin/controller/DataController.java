package com.tour.admin.controller;

import com.tour.admin.repository.write.LoadWriteRepository;
import com.tour.admin.service.LoadService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@Controller
public class DataController {

    private final LoadService service;

    @Autowired
    public DataController(LoadService service) {
        this.service = service;
    }
/*
    @GetMapping("/data/location")
    @ResponseBody
    public JSONObject dataLocation() throws Exception{
        String result = "";
        URL url = new URL("https://apis.data.go.kr/B551011/KorService1/areaCode1?numOfRows=100&MobileOS=ETC&MobileApp=andogn-tour&_type=json&serviceKey=n0pWWW%2B4YGIcdLVSRvboh%2BCDWiK7X%2B8t335b6p56e%2BOP7KGLpVUjmwbTh1Rme4ZeXCJHEo1Tidrw6DpWmZjQqQ%3D%3D");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-type", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = br.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(result);

        System.out.println();
        return  (JSONObject) json.get("response");
    }*/

    @GetMapping("/data/store")
    @ResponseBody
    public JSONObject data() throws Exception{
//        String result = "";
//        URL url = new URL("https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=133&pageNo=1&MobileOS=ETC&MobileApp=andong-tour&_type=json&listYN=Y&arrange=B02&areaCode=35&sigunguCode=11&cat1=A05&serviceKey=n0pWWW%2B4YGIcdLVSRvboh%2BCDWiK7X%2B8t335b6p56e%2BOP7KGLpVUjmwbTh1Rme4ZeXCJHEo1Tidrw6DpWmZjQqQ%3D%3D&cat2=A0502&cat3=A05020700");
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setRequestMethod("GET");
//        urlConnection.setRequestProperty("Content-type", "application/json");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//        result = br.readLine();
//
//        JSONParser jsonParser = new JSONParser();
//        JSONObject json = (JSONObject) jsonParser.parse(result);
//        JSONObject response = (JSONObject) json.get("response");
//        JSONObject body = (JSONObject) response.get("body");
//
//        JSONObject items = (JSONObject)body.get("items");
//        JSONArray item = (JSONArray) items.get("item");
//
//        /*for (Object obj: item) {
//
//            Map<String, Object> params = (JSONObject) obj;
//            writeRepository.storeInsert(params);
//        }*/
//
//        service.storeInsert(item);
        return null;
    }

    @GetMapping("/data/detail")
    @ResponseBody
    public JSONObject dataDetail() throws Exception{
        //service.detailInsert();
        System.out.println("detail 은 온다!!!!!!!!!");
        return null;
    }

    @GetMapping("/data/json")
    @ResponseBody
    public JSONObject jsonTest(){
        String file = "/usr/local/tomcat9/webapps/data/file/json/1.json";
        System.out.println("file : " + file);
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonStr = null;
        try {
            jsonStr = (String) parser.parse(reader);
            json = (JSONObject) parser.parse(jsonStr);
            JSONObject arr = (JSONObject) json.get("data");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        return json;
    }




/*
    @GetMapping("/data/category")
    @ResponseBody
    public JSONObject dataCategory() throws Exception{
        String result = "";
        URL url = new URL("https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=133&pageNo=1&MobileOS=ETC&MobileApp=andong-tour&_type=json&listYN=Y&arrange=A&" +
                "contentTypeId=12&areaCode=35" +
                "&sigunguCode=11" +
                "&cat1=A01&" +
                "serviceKey=n0pWWW%2B4YGIcdLVSRvboh%2BCDWiK7X%2B8t335b6p56e%2BOP7KGLpVUjmwbTh1Rme4ZeXCJHEo1Tidrw6DpWmZjQqQ%3D%3D");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-type", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = br.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(result);

        return (JSONObject) json.get("response");
    }*/


}
