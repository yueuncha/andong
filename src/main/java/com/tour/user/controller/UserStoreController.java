package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/store")
@Controller
public class UserStoreController {

    private final UserStoreServiceImpl storeService;

    @Autowired
    public UserStoreController(UserStoreServiceImpl storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/category")
    @ResponseBody
    public Map<String, Object> categoryList(@RequestBody Map<String, Object> params){
        return storeService.categoryList(params);
    }

    @PostMapping("/storeSearch")
    @ResponseBody
    public List<Map<String, Object>> storeSearch(Map<String, Object> params){
        return storeService.storeSearch(params);
    }

    @GetMapping("/categoryDetail")
    @ResponseBody
    public Map<String, Object> categoryDetail(String str_category, String lang, String mb_idx){
        return storeService.categoryDetail(str_category, lang, mb_idx);
    }

    @GetMapping("/storeDetail")
    @ResponseBody
    public Map<String, Object> storeDetail(String str_idx, String lang, String mb_idx){
        return storeService.storeDetail(str_idx, lang, mb_idx);
    }

    @GetMapping("/storeViewCnt")
    @ResponseBody
    public Map<String, Object> storeView(String str_idx, String mb_idx){
        return storeService.storeView(str_idx, mb_idx);
    }

    @PostMapping("/storeLike")
    @ResponseBody
    public Map<String, Object> storeLike(@RequestBody Map<String, Object> params){
        return storeService.storeLike(params);
    }

    @PostMapping("/reviewWrite")
    @ResponseBody
    public Map<String, Object> strReviewWrite(@RequestBody Map<String, Object> params){
        return storeService.strReviewCreate(params);
    }
    @GetMapping(value = "/image", produces= MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(
                           @RequestParam("value") String value) // A
            throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

//        String[] fileAr = value.split("_");
//        String filePath = fileAr[0];
                //"D:\\eGovFrameDev-4.0.0-64bit\\workspace\\andong\\src\\main\\webapp\\resources\\image\\place\\"
        String fileDir = "/usr/local/tomcat9/webapps/image/place" + value ; // 파일경로


        try{
            fis = new FileInputStream(fileDir);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fis.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch(IOException e){
            throw new RuntimeException("File Error");
        }
        return fileArray;
    }

}
