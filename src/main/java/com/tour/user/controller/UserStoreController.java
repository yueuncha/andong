package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import com.tour.user.vo.RequestVO;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequestMapping("/store")
@Controller
public class UserStoreController {
    @Value("#{address['ip']}")
    private String ip;
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

    @PostMapping("/search")
    @ResponseBody
    public Map<String, Object> storeSearch(RequestVO vo) throws Exception{
        return storeService.storeSearch(vo);
    }

    @PostMapping("/keyword")
    @ResponseBody
    public Map<String, Object> bestKeyword(RequestVO vo) throws Exception{
        return storeService.bestKeyword(vo);
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

    @PostMapping("/experList")
    @ResponseBody
    public Map<String, Object> experienceList(RequestVO vo) throws Exception{
        return storeService.experienceList(vo);
    }

    @PostMapping("/experDetail")
    @ResponseBody
    public Map<String, Object> experienceDetail(RequestVO vo) throws Exception{
        return storeService.experienceDetail(vo);
    }

    @PostMapping("/experView")
    @ResponseBody
    public Map<String, Object> experienceView(RequestVO vo) throws Exception{
        return storeService.experienceView(vo);
    }

    @PostMapping("/festivalList")
    @ResponseBody
    public Map<String, Object> festivalList(RequestVO vo) throws Exception{
        return storeService.festivalList(vo);
    }

    @PostMapping("/festivalView")
    @ResponseBody
    public Map<String, Object> festivalView(RequestVO vo) throws Exception{
        return storeService.festivalView(vo);
    }

    @PostMapping("/reviewWrite")
    @ResponseBody
    public Map<String, Object> strReviewWrite(MultipartHttpServletRequest files, RequestVO vo) throws Exception{
        return storeService.strReviewCreate(files, vo);
    }

    @PostMapping("/chart")
    @ResponseBody
    public Map<String, Object> chartList( RequestVO vo) throws Exception{
        return storeService.chartList(vo);
    }

    @PostMapping("/review")
    @ResponseBody
    public Map<String, Object> reviewList( RequestVO vo) throws Exception{
        return storeService.reviewList(vo);
    }

    @PostMapping("/reviewUpdate")
    @ResponseBody
    public Map<String, Object> reviewUpdate(MultipartHttpServletRequest multipart ,RequestVO vo) throws Exception{
        return storeService.reviewUpdate(multipart,vo);
    }

    @PostMapping("/reviewDelete")
    @ResponseBody
    public Map<String, Object> reviewDelete( RequestVO vo) throws Exception{
        return storeService.reviewDelete(vo);
    }

    @PostMapping("/rvImgDelete")
    @ResponseBody
    public Map<String, Object> rvImageDelete(RequestVO vo) throws Exception{
        return storeService.rvImageDelete(vo);
    }

    @PostMapping("/menu")
    @ResponseBody
    public Map<String, Object> menuList(RequestVO vo) throws Exception{
        return storeService.menuList(vo);
    }


    @PostMapping("/menu/one")
    @ResponseBody
    public Map<String, Object> menuOne(RequestVO vo) throws Exception{
        return storeService.menuOne(vo);
    }

    @PostMapping("/randomBanner")
    @ResponseBody
    public Map<String, Object> festivalBanner(RequestVO vo) throws Exception{
        return storeService.festivalBanner(vo);
    }

    @PostMapping("/experience")
    @ResponseBody
    public Map<String, Object> mainExperience(RequestVO vo) throws Exception{
        return storeService.mainExperience(vo);
    }

    /*
    * 이미지 조회(해결)/ 메뉴 조회 / 리뷰 별점/ 통계/ 패스 저장 / 패스 추가 / 일정 추가 / 축제체험 조회 /
    *
    * */

    @PostMapping("/andongStoryTest")
    @ResponseBody
    public JSONObject andongStoryTest(@RequestParam MultipartFile file) throws Exception {
        //System.out.println(values);
        System.out.println(file.getOriginalFilename());
        String saveFileName = "", saveFilePath = "";
        String folderName = "/usr/local/tomcat9/webapps/data/file/image/story";
        MultipartFile multipartFile = file;
        JSONObject jsonObject = new JSONObject();

        String fileName = multipartFile.getOriginalFilename();
        String fileCutName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
        saveFilePath = folderName+'/' + fileName;

        System.out.println(saveFilePath);

        File fileFolder = new File(folderName);

        if(!fileFolder.exists()){
            if(fileFolder.mkdirs()){
                System.out.println("file mkdirs : SUCCESS");
            }else{
                System.out.println("file mkdirs : FAIL");
            }
        }

        File saveFile = new File(saveFilePath);

        if(saveFile.isFile()){
            boolean _exist = true;

            int index = 0;

            while(_exist){
                index ++;

                saveFileName = fileCutName + "(" + index + ")." + fileExt;
                String dictFile = folderName + '/'+ saveFileName;
                _exist = new File(dictFile).isFile();
                if(!_exist){
                    saveFilePath = dictFile;
                }
            }
            System.out.println(saveFilePath);
            multipartFile.transferTo(new File(saveFilePath));
            jsonObject.put("url", ip+"/image/story/"+saveFileName);
            jsonObject.put("responseCode", "success");
        }else{
            System.out.println(saveFile.getPath());
            multipartFile.transferTo(saveFile);
            jsonObject.put("url", ip+"/image/story/"+fileName);
            jsonObject.put("responseCode", "success");
        }
        return jsonObject;
    }

    @RequestMapping("/andongStorytext")
    @ResponseBody
    public Map<String, Object> textTest(@RequestBody Map<String, Object> values) throws Exception{
//        JSONParser parser = new JSONParser();
//        JSONObject json = (JSONObject) parser.parse(values);
//        System.out.println(json);
        System.out.println(values);

        return storeService.storyTextSave(values);
    }


}
