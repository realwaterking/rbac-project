package com.aqua.rbaccore.service;

import com.aqua.rbaccore.model.dto.admin.*;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.model.entity.Role;

import java.util.List;

/**
 * @author water king
 * @time 2024/2/12
 */
public interface AdminService {

    /**
     * 设置用户的角色
     * @param userRoleAddRequest
     * @return
     */
    Long addUserRole(UserRoleAddRequest userRoleAddRequest);

    /**
     * 删除用户的角色信息
     * @param userRoleDeleteRequest
     * @return
     */
    Boolean deleteUserRole(UserRoleDeleteRequest userRoleDeleteRequest);


    /**
     * 获得用户的角色信息
     * @param userRoleGetRequest
     * @return
     */
    List<Role> getUserRole(UserRoleGetRequest userRoleGetRequest);

    /**
     * 给角色赋予权限
     * @param rolePermissionAddRequest
     * @return
     */
    Boolean addRolePermission(RolePermissionAddRequest rolePermissionAddRequest);

    /**
     * 删除角色所拥有的某些权限
     * @param rolePermissionDeleteRequest
     * @return
     */
    Boolean deleteRolePermission(RolePermissionDeleteRequest rolePermissionDeleteRequest);

    /**
     * 获得该角色所拥有的所有权限
     * @param rolePermissionGetRequest
     * @return
     */
    List<Permission> getRolePermission(RolePermissionGetRequest rolePermissionGetRequest);
}
