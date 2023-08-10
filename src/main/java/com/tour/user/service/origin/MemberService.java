package com.tour.user.service.origin;

import com.tour.user.vo.MemberVO;
import org.json.simple.JSONObject;

import java.util.Map;

public interface MemberService {

    //이용약관
    Map<String, String> userAgreement();
    //회원가입
    JSONObject userJoin(MemberVO vo, Map<String, Object> map);
    //전체회원조회
    JSONObject userList( Map<String, Object> map);
    //특정회원조회
    JSONObject userOne(int mb_idx, Map<String, Object> map);
    //회원로그인
    JSONObject userLogin(String mb_id, String mb_pw, Map<String, Object> map) throws Exception;
    //회원중복체크
    JSONObject userDupChk(String mb_param, String mb_value, Map<String, Object> map);
    //회원특정데이터조회
    JSONObject userDataOne(String mb_param, int mb_idx, Map<String, Object> map);
    //이메일발송
    JSONObject mailSend(String email, Map<String, Object> map);
    //비밀번호변경
    JSONObject passwordChange(Map<String, Object> param);



}
