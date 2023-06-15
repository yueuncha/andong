package com.tour.admin.member.vo;



import lombok.Data;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MemberVO {

    private int mb_idx;
    private String mb_id;
    private String mb_pw;
    private String mb_email;
    private String mb_ci;
    private char mb_gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String mb_birth;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String mb_regdate;

    private int mb_level;
    private int mb_status;
    private String mb_nickname;
    private String mb_local;
    private char mb_foreigner;

    private String mb_param;
    private String mb_value;

}
