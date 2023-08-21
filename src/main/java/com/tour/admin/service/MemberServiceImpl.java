package com.tour.admin.service;

import com.tour.AES128;
import com.tour.admin.repository.read.MemberReadRepository;
import com.tour.admin.repository.write.MemberWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Random;

@Service
public class MemberServiceImpl implements MemberService{

    @Value("#{aesConfig['key']}")
    private String key;
    @Value("#{aesCrypt['cryptkey']}")
    private String cryptkey;

    private final MemberReadRepository readRepository;
    private final MemberWriteRepository writeRepository;

    @Autowired
    public MemberServiceImpl(MemberReadRepository readRepository, MemberWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Override
    public ModelAndView managerView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/member/managerView");
        mv.addObject("List",readRepository.managerList());
        return mv;
    }

    @Override
    public int managerSave(Map<String, Object> params) {
        try{
            AES128 aes = new AES128(key, cryptkey);
            String password = aes.javaEncrypt(params.get("mb_pw").toString());
            params.replace("mb_pw", password);
        }catch (Exception e){}
        System.out.println(params);
        return writeRepository.managerInsert(params);
    }

    @Override
    public Map<String, Object> managerOne(int mb_idx) {
        return readRepository.managerOne(mb_idx);
    }

    @Override
    public int mailSend(String email) {
        Random random = new Random();
        int resNum = random.nextInt(888888)+111111;

        String contents = "인증번호 :" ;

        //Message message =

        return resNum;
    }
}
