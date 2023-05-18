package com.example.demo.model.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    private Long id;

    private String userAccount;

    private String userName;

    private String userRole;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
