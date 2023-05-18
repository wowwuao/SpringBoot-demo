package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.user.dto.UserLoginRequest;
import com.example.demo.model.user.dto.UserRegisterRequest;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    long userRegister(UserRegisterRequest userRegisterRequest);

    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    UserVO getLoginUserVO(User user);

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User user);

}
