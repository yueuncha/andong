package com.tour.user.board.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
public class MemberVO {

    private String mb_idx;

    private String mb_id;

    private String mb_pw;

    private String mb_email;

    private String mb_ci;

    private String mb_gender;

    private String mb_birth;

    private String mb_regdate;

    private String mb_level;

    private String mb_status;

    private String mb_nickname;

    private String mb_local;

    private String mb_foreigner;

    private String mb_param;
    private String mb_value;


}
