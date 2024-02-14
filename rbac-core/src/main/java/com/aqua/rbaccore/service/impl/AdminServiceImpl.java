package com.aqua.rbaccore.service.impl;

import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.mapper.PermissionMapper;
import com.aqua.rbaccore.mapper.RoleMapper;
import com.aqua.rbaccore.mapper.RolePermissionMapper;
import com.aqua.rbaccore.mapper.UserRoleMapper;
import com.aqua.rbaccore.model.dto.admin.*;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.entity.RolePermission;
import com.aqua.rbaccore.model.entity.UserRole;
import com.aqua.rbaccore.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/12
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Long addUserRole(UserRoleAddRequest userRoleAddRequest) {
        Long result = null;
        try {
            Long userId = userRoleAddRequest.getUserId();
            Long roleId = userRoleAddRequest.getRoleId();
            if (userId <= 0 || roleId <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户id或角色id有误");
            }

            // 检查用户是否已经拥有该角色
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId).eq("roleId", roleId);
            Long count = userRoleMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户已经拥有该角色");
            }

            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            result = (long) userRoleMapper.insert(userRole);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Boolean deleteUserRole(UserRoleDeleteRequest userRoleDeleteRequest) {
        try {
            Long userId = userRoleDeleteRequest.getUserId();
            if (userId <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户id有误");
            }
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            userRoleMapper.delete(queryWrapper);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Role> getUserRole(UserRoleGetRequest userRoleGetRequest) {
        Long userId = userRoleGetRequest.getUserId();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<UserRole> userRoleList = userRoleMapper.selectList(queryWrapper);
        List<Role> list = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            Long roleId = userRole.getRoleId();
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("id", roleId);
            Role role1 = roleMapper.selectOne(wrapper);
            list.add(role1);
        }
        return list;
    }

    @Override
    public Boolean addRolePermission(RolePermissionAddRequest rolePermissionAddRequest) {
        try {
            Long roleId = rolePermissionAddRequest.getRoleId();
            List<Long> permissionIds = rolePermissionAddRequest.getPermissionId();
            if (roleId <= 0 || permissionIds == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                rolePermissionMapper.insert(rolePermission);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean deleteRolePermission(RolePermissionDeleteRequest rolePermissionDeleteRequest) {
        try {
            Long roleId = rolePermissionDeleteRequest.getRoleId();
            List<Long> permissionIds = rolePermissionDeleteRequest.getPermissionId();
            if (roleId <= 0 || permissionIds == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            for (Long permissionId : permissionIds) {
                QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("roleId",roleId);
                queryWrapper.eq("permissionId", permissionId);
                Long result = (long) rolePermissionMapper.delete(queryWrapper);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Permission> getRolePermission(RolePermissionGetRequest rolePermissionGetRequest) {
        List<Permission> list;

        try {
            list = new ArrayList<>();
            Long roleId = rolePermissionGetRequest.getRoleId();
            if (roleId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("roleId", roleId);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper);

            if (!rolePermissions.isEmpty()) {
                List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
                permissionQueryWrapper.in("id", permissionIds);
                list = permissionMapper.selectList(permissionQueryWrapper);
            }
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
