package com.tour.user.service.origin;

import com.tour.user.vo.RequestVO;
import org.json.simple.JSONObject;

import java.util.Map;

public interface MemberService {

    //암호화테스트
    Map<String, Object> test(RequestVO vo) throws Exception;
    //이용약관
    Map<String, Object> userAgreement();
    //회원가입
    Map<String, Object> userJoin(RequestVO vo) throws Exception;
    //전체회원조회
    Map<String, Object> userList() throws Exception;
    //특정회원조회
    Map<String, Object> userOne(RequestVO vo) throws Exception;
    //회원로그인
    Map<String, Object> userLogin(RequestVO vo) throws Exception;
    //회원중복체크
    Map<String, Object> userDupChk(RequestVO vo) throws Exception;
    //회원특정데이터조회
    Map<String, Object> userDataOne(RequestVO vo) throws Exception;
    //이메일발송
    Map<String, Object> mailSend(RequestVO vo)throws Exception;
    //비밀번호변경
    Map<String, Object> passwordChange(RequestVO vo) throws Exception;

    Map<String, Object> localCategory(RequestVO vo) throws Exception;
    Map<String, Object> localChoice(RequestVO vo) throws Exception;
    Map<String, Object> newSession(RequestVO vo) throws Exception;
    Map<String, Object> sessionChk(RequestVO vo) throws Exception;
    Map<String, Object> memberEmailChk (RequestVO vo) throws Exception;

    Map<String, Object> alarmCheck(RequestVO vo) throws Exception;
    Map<String, Object> snsUserLogin(RequestVO vo) throws Exception;
    Map<String, Object> userPushList(RequestVO vo) throws Exception;
}
