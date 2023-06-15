package com.tour.user.board.service;

import com.tour.AES128;
import com.tour.user.board.repository.UserMainRepository;
import com.tour.user.board.vo.MemberVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserMainServiceImpl implements MainService{

    @Value("#{aesConfig['key']}")
    private String key;
    private final UserMainRepository userMainRepository;

    @Autowired
    public UserMainServiceImpl(UserMainRepository userMainRepository) {
        this.userMainRepository = userMainRepository;
    }

    /**
     * 입력 받은 컬럼명 확인
     * */
    public String sqlParamChk(String mb_param) {
        try {
            mb_param = new MemberVO().getClass().getDeclaredField(mb_param).getName();
        } catch (NoSuchFieldException e) {
            mb_param = null;
        }
        return mb_param;
    }

    /**
     * 전체 회원 조회
     * */
    @Override
    public JSONObject userList(Map<String, Object> map){
        map.put("result", userMainRepository.selectUserList());
        return new JSONObject(map);
    }

    /**
     * 특정 회원 조회
     * @param mb_idx
     * */
    @Override
    public JSONObject userOne(int mb_idx, Map<String, Object> map){
        MemberVO vo = userMainRepository.selectUserOne(mb_idx);
        map.put("result", (vo != null) ? vo : Collections.singletonMap("파라미터 확인",mb_idx));
        return new JSONObject(map);
    }

    /**
     * 회원 로그인
     * @param mb_id mb_pw
     * */
    @Override
    public JSONObject userLogin(String mb_id, String mb_pw, Map<String, Object> map){
        try {
            AES128 aes128 = new AES128(key);
            Optional<MemberVO> vo = Optional.ofNullable(userMainRepository.userLogin(mb_id));
            boolean txt = (new EqualsBuilder().append(aes128.javaDecrypt(vo.get().getMb_pw()), mb_pw)
                    .isEquals()) ? true : false;

            map.put("loginResult",txt);
            map.put("result", (!txt) ? "" : vo);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("loginResult", "N");
        }

        return new JSONObject(map);
    }

    /**
     * 중복체크
     * @oaran
     * mb_param : 중복체크 대상의 컬럼명
     * mb_value : 중복체크 대상의 값
     * */
    @Override
    public JSONObject userDupChk(String mb_param, String mb_value, Map<String, Object> map){
        String param = sqlParamChk(mb_param);

        int resNum = (param != null) ? Optional.ofNullable(userMainRepository.userDupChk(param, mb_value)).orElseGet(() -> 0) : 0;
        String result = (param != null) ? ((resNum != 1 ) ? "Y" : "N") : "파라미터 확인";
        map.put("result", Collections.singletonMap(mb_param, result));

        return new JSONObject(map);
    }

    /**
     * 특정 컬럼 값 가져오기
     * @param
     * mb_param : 컬럼명
     * mb_idx : 회원고유번호
     * */
    @Override
    public JSONObject userDataOne(String mb_param, int mb_idx, Map<String, Object> map) {
        String param = sqlParamChk(mb_param);
        MemberVO vo = (param != null) ? Optional.ofNullable(userMainRepository.userDataOne(param, mb_idx)).orElseGet(null) : null;
        map.put("result", (vo != null) ? vo : Collections.singletonMap( mb_param, "파라미터 확인"));
        return new JSONObject(map);
    }

    /**
     * 회원가입
     * @param vo MemberVO
     * */
    @Override
    public JSONObject userJoin(MemberVO vo, Map<String, Object> map) {
        int result = (vo != null) ? 1 : 0;

        try {
            AES128 aes128 = new AES128(key);

            if(result != 0){
                String password = aes128.javaEncrypt(vo.getMb_pw());
                vo.setMb_pw(password);
                result = Optional.ofNullable(userMainRepository.userJoin(vo)).orElseGet(() -> 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("result", Collections.singletonMap("joinResult", (result != 0) ? "N" : "Y"));
        return new JSONObject(map);
    }
}