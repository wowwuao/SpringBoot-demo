package com.example.demo.model.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String userRole;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
