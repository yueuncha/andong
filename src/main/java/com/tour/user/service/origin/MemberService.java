package com.tour.user.service.origin;

import com.tour.user.vo.MemberVO;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface MemberService {

    //이용약관
    Map<String, String> userAgreement();
    //회원가입
    JSONObject userJoin(MemberVO vo);
    //전체회원조회
    Map<String, Object> userList();
    //특정회원조회
    Map<String, Object> userOne(int mb_idx);
    //회원로그인
    JSONObject userLogin(Map<String, Object> params) throws Exception;
    //회원중복체크
    JSONObject userDupChk(String mb_param, String mb_value);
    //회원특정데이터조회
    JSONObject userDataOne(String mb_param, int mb_idx);
    //이메일발송
    JSONObject mailSend(String email);
    //비밀번호변경
    JSONObject passwordChange(Map<String, Object> param);

    Map<String, Object> localCategory(String mb_foreign);
    Map<String, Object> localChoice(String set_1_code);

}
