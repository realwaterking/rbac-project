package com.aqua.rbaccore.service;

import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccore.model.dto.user.UserLoginRequest;
import com.aqua.rbaccore.model.dto.user.UserRegisterRequest;
import com.aqua.rbaccore.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 70742
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-02-05 23:26:10
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户注销账户
     * @param deleteRequest
     * @return
     */
    Boolean deleteUser(DeleteRequest deleteRequest);
}
