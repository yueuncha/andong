package com.tour.user.board.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.*;

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


}
