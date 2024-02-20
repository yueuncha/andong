package com.tour.admin.service;

import com.tour.admin.repository.read.AppManageReadRepository;
import com.tour.admin.repository.write.AppManageWriteRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppManageServiceImpl implements AppManageService{

    private final AppManageReadRepository readRepository;
    private final AppManageWriteRepository writeRepository;

    @Autowired
    public AppManageServiceImpl(AppManageReadRepository readRepository, AppManageWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Override
    public List<Map<String, Object>> andongStoryList() throws Exception {
        return readRepository.andongStoryList();
    }

    @Override
    public Map<String, Object> andongStoryView(int tour_idx) throws Exception {
        return readRepository.andongStoryView(tour_idx);
    }

    @Override
    public List<Map<String, Object>> bannerLoad() throws Exception {
        return readRepository.bannerLoad();
    }

    @Override
    public boolean bannerLoadAdd() throws Exception {

        String [] url_list = {"","_2","_3","_4","_5", "_6"};

        for (int i = 0; i < url_list.length; i++) {
            Map<String, Object> param = new HashMap<>();
            String URL = "https://www.tourandong.com/public/sub2/sub1"+url_list[i]+".cshtml";

            try {
                Connection conn = Jsoup.connect(URL);

                Document html = conn.post(); // conn.get();

                Element fileblock = html.getElementById("subRightDiv");
                Elements title = fileblock.getElementsByTag("h3");
                Elements image = fileblock.getElementsByTag("img");

                param.put("bn_url", conn.get().baseUri());
                param.put("bn_title", title.text());
                param.put("bn_img", "https://www.tourandong.com"+image.attr("src"));

                writeRepository.bannerLoadAdd(param);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    @Override
    public int bannerUpdate(Map<String, Object> banner) throws Exception {
        System.out.println(banner);
        return writeRepository.bannerUpdate(banner);
    }

    @Override
    public int bannerDelete(int bn_idx) throws Exception {
        return writeRepository.bannerDelete(bn_idx);
    }

    @Override
    public List<Map<String, Object>> stampList() throws Exception {
        return readRepository.stampList();
    }

    @Override
    public List<Map<String, Object>> stampUserList() throws Exception {
        return readRepository.stampUserList();
    }

    @Override
    public List<Map<String, Object>> pushSendList() {
        return readRepository.pushSendList(null);
    }

    @Override
    public List<Map<String, Object>> recommendTourList() {
        return readRepository.recommendTourList();
    }

    @Override
    public List<Map<String, Object>> boardUserList() {
        return readRepository.boardUserList();
    }
}
