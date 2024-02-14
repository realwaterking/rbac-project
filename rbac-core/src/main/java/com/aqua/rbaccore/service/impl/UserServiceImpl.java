package com.aqua.rbaccore.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.mapper.UserMapper;
import com.aqua.rbaccore.model.dto.user.UserLoginRequest;
import com.aqua.rbaccore.model.dto.user.UserRegisterRequest;
import com.aqua.rbaccore.model.entity.User;
import com.aqua.rbaccore.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.aqua.rbaccore.constant.RoleConstant.USER_LOGIN_STATE;

/**
* @author 70742
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:10
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值, 混淆密码,
     */
    public static final String SALT = "i love you, the beautiful girl";

    @Override
    public User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        //2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在的情况
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或者密码错误");
        }
        //3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        //remove login state
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        //先判断是否登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //从数据库查询(最求性能的话可以注释,直接走缓存)
        Long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String username = userRegisterRequest.getUsername();
        String location = userRegisterRequest.getLocation();
        String phoneNumber = userRegisterRequest.getPhoneNumber();
        // 1. 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isAnyBlank(location, username, userAccount,userPassword, checkPassword, phoneNumber)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度不能小于4位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码不能小于八位");
        }
        if(!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        if (!phoneNumber.matches("^1[3, 5, 6, 7, 8, 9]\\d{9}$")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号格式不正确");
        }

        synchronized (userAccount.intern()) {
            //1. 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            Long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            //2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            //3. 分配accessKey, secretKey
            String accessKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(8));
            //4. 插入数据
            User user = new User();
            user.setUsername(username);
            user.setUserAccount(userAccount);
            user.setPhoneNumber(phoneNumber);
            user.setUserPassword(encryptPassword);
            user.setLocation(location);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败, 数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public Boolean deleteUser(DeleteRequest deleteRequest) {
        try {
            if (deleteRequest.getId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", deleteRequest.getId());
            User user = userMapper.selectOne(queryWrapper);
            user.setIsDelete(1);
            return true;
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
}




