package com.example.consumer.controller;

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
    public UserVO queryOrderByUserId(@PathVariable("id") Long id) {
        // 根据id查询订单并返回
        return userClient.findById(id);
    }
}

