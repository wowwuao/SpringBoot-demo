package com.example.demo.model.user.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String userPassword;

    private String userRole;

}
