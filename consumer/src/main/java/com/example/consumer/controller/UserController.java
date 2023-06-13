package com.example.consumer.controller;

import com.example.common.base.BaseResponse;
import com.example.common.exception.BusinessException;
import com.example.common.utils.ResultUtils;
import com.example.feignapi.model.UserVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.feignapi.clients.UserClient;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserClient userClient;

    @GetMapping("/{id}")
    public BaseResponse<UserVO> queryOrderByUserId(@PathVariable("id") Long id) {
        // 根据id查询订单并返回
        try {
            UserVO userVo = userClient.findById(id);
            return ResultUtils.success(userVo);
        }catch (BusinessException e){
            return ResultUtils.error(e.hashCode(),"查询出现错误");
        }
    }
}

