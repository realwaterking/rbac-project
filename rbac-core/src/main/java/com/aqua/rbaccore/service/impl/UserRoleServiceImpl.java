package com.aqua.rbaccore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbaccore.model.entity.UserRole;
import com.aqua.rbaccore.service.UserRoleService;
import com.aqua.rbaccore.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【user_role(用户角色关系表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:12
*/
@Service
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




