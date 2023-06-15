package com.tour.user.board.service;

import com.tour.user.board.vo.MemberVO;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;

public interface MainService {


    // 회원가입
    JSONObject userJoin(MemberVO vo, Map<String, Object> map);

    // 전체회원조회
    JSONObject userList( Map<String, Object> map);

    //특정 회원 전체조회
    JSONObject userOne(int mb_idx, Map<String, Object> map);

    // 로그인
    JSONObject userLogin(String mb_id, String mb_pw, Map<String, Object> map) throws Exception;

    // 중복체크
    JSONObject userDupChk(String mb_param, String mb_value, Map<String, Object> map);

    JSONObject userDataOne(String mb_param, int mb_idx, Map<String, Object> map);


    // 비밀번호 찾기

    // 접속 로그 추가
    //

}
