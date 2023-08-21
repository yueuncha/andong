package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import com.tour.user.vo.RequestVO;
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
    public Map<String, Object> categoryList(RequestVO vo) throws Exception{
        return storeService.categoryList(vo);
    }

    @PostMapping("/storeSearch")
    @ResponseBody
    public List<Map<String, Object>> storeSearch(RequestVO vo) throws Exception{
        return storeService.storeSearch(vo);
    }

    @PostMapping("/categoryDetail")
    @ResponseBody
    public Map<String, Object> categoryDetail(RequestVO vo) throws Exception{
        return storeService.categoryDetail(vo);
    }

    @PostMapping("/storeDetail")
    @ResponseBody
    public Map<String, Object> storeDetail(RequestVO vo) throws Exception{
        return storeService.storeDetail(vo);
    }

    @PostMapping("/storeViewCnt")
    @ResponseBody
    public Map<String, Object> storeView(RequestVO vo) throws Exception{
        return storeService.storeView(vo);
    }

    @PostMapping("/storeLike")
    @ResponseBody
    public Map<String, Object> storeLike(RequestVO vo) throws Exception{
        return storeService.storeLike(vo);
    }

    @PostMapping("/reviewWrite")
    @ResponseBody
    public Map<String, Object> strReviewWrite(RequestVO vo) throws Exception{
        return storeService.strReviewCreate(vo);
    }
    @GetMapping(value = "/image", produces= MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(
                           @RequestParam("value") String value) //
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


    /*
    * 이미지 조회/ 메뉴 조회 / 리뷰 별점/ 통계/ 패스 저장 / 패스 추가 / 일정 추가 / 축제체험 조회 /
    *
    * */




}
