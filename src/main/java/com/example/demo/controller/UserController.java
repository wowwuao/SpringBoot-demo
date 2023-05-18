package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.*;
import com.example.demo.model.user.dto.UserLoginRequest;
import com.example.demo.model.user.dto.UserQueryRequest;
import com.example.demo.model.user.dto.UserRegisterRequest;
import com.example.demo.model.user.dto.UserUpdateRequest;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserVO;
import com.example.demo.service.UserService;
import com.example.demo.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        try{
            long result = userService.userRegister(userRegisterRequest);
            return ResultUtils.success(result);
        }catch (BusinessException e){
            return ResultUtils.error(e.hashCode(),e.getMessage());
        }
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try{
            UserVO loginUserVO = userService.userLogin(userLoginRequest, request);
            return ResultUtils.success(loginUserVO);
        }catch (Exception e){
            return ResultUtils.error(e.hashCode(),e.getMessage());
        }
    }

    /**
     * 获取当前登录用户 (脱敏)
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        User user = userService.getById(id);
        if (user == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest,
                                                     HttpServletRequest request) {
        if (userUpdateRequest == null || userUpdateRequest.getId() <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        // 参数校验
        long id = userUpdateRequest.getId();
        // 判断是否存在
        User oldUser = userService.getById(id);
        if (oldUser == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        User currentUser = userService.getLoginUser(request);
        // 仅本人或管理员可修改
        if (currentUser.getId() != id && !userService.isAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userService.updateById(user);
        return ResultUtils.success(result);
    }

    @GetMapping("/{id}")
    public BaseResponse<UserVO> userQueryById(@PathVariable("id") Long id){
        try{
            User user = userService.getById(id);
            UserVO userVo = new UserVO();
            BeanUtils.copyProperties(user, userVo);
            return ResultUtils.success(userVo);
        }
        catch (BusinessException e){
            return ResultUtils.error(e.hashCode(), e.getMessage());
        }
    }

    @GetMapping("/list/page")
    public BaseResponse<Page<User>> userListByPage(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User userQuery = new User();
        ConvertUtils.emptyToNull(userQueryRequest);
        BeanUtils.copyProperties(userQueryRequest, userQuery);
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        String userAccount = userQuery.getUserAccount();
        String userName = userQuery.getUserName();
        // 支持模糊搜索
        userQuery.setUserAccount(null);
        userQuery.setUserName(null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        queryWrapper.like(StringUtils.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        // 按照 最新修改时间 降序排列
        queryWrapper.orderByDesc( "updateTime");
        try{
            Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
            return ResultUtils.success(userPage);
        }catch (BusinessException e){
            return ResultUtils.error(e.hashCode(),e.getMessage());
        }
    }
}
