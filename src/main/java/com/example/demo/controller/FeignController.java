package com.example.demo.controller;

import com.example.common.base.BaseResponse;
import com.example.common.exception.BusinessException;
import com.example.common.model.ErrorCode;
import com.example.common.utils.ResultUtils;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserVO;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  供 Feign 远程调用
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserVO userQueryByIdUsingFeign(@PathVariable("id") Long id){
            User user = userService.getById(id);
            UserVO userVo = new UserVO();
            if(user != null){
                BeanUtils.copyProperties(user, userVo);
            }
            return userVo;
    }
}
