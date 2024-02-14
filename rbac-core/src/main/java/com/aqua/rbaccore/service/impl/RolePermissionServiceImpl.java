package com.aqua.rbaccore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aqua.rbaccore.model.entity.RolePermission;
import com.aqua.rbaccore.service.RolePermissionService;
import com.aqua.rbaccore.mapper.RolePermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 70742
* @description 针对表【role_permission(角色权限关系表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:07
*/
@Service
@Slf4j
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




