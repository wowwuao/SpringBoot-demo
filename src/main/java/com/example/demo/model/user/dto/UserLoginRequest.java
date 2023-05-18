package com.example.demo.model.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1234567891011121314L;

    private String userAccount;

    private String userPassword;
}
