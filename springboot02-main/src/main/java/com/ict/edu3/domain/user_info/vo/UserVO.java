package com.ict.edu3.domain.user_info.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private int user_idx;
    private String user_id;
    private String user_pw;
    private String user_profile;
    private Date user_reg_date;
    private String user_name;
    private String user_nickname;
    private String user_email;
    private String user_phone;
    private Date user_birth_date;
    private String user_gender;
    private Date user_out_date;
    private String user_out_reason;
    private Date user_last_login;

}
