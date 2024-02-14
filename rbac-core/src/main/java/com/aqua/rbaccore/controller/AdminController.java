package com.aqua.rbaccore.controller;

import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.model.dto.admin.*;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.vo.PermissionVO;
import com.aqua.rbaccore.model.vo.RoleVO;
import com.aqua.rbaccore.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/12
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 给用户分配角色
     * @param userRoleAddRequest
     * @return
     */
    @PostMapping("/link/add")
    @AuthCheck(permissionName = "管理员给用户添加角色的权限", requirePermission = "admin:setUserRole")
    public BaseResponse<Long> addUserRole(@RequestBody UserRoleAddRequest userRoleAddRequest) {
        if (userRoleAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userRoleAddRequest.getUserId() <= 0 || userRoleAddRequest.getRoleId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long result = adminService.addUserRole(userRoleAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户被分配的角色
     * @param userRoleDeleteRequest
     * @return
     */
    @PostMapping("/link/delete")
    @AuthCheck(permissionName = "删除用户所拥有的角色", requirePermission = "admin:deleteUserRole")
    public BaseResponse<Boolean> deleteUserRole(@RequestBody UserRoleDeleteRequest userRoleDeleteRequest) {
        if (userRoleDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userRoleDeleteRequest.getUserId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = adminService.deleteUserRole(userRoleDeleteRequest);
        return ResultUtils.success(b);
    }

    /**
     * 查询用户所拥有的角色
     * @param userRoleGetRequest
     * @return
     */
    @PostMapping("/link/get")
    @AuthCheck(permissionName = "获得用户所拥有的角色", requirePermission = "admin:getUserRole")
    public BaseResponse<List<RoleVO>> getUserRole(@RequestBody UserRoleGetRequest userRoleGetRequest) {
        if (userRoleGetRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userRoleGetRequest.getUserId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Role> userRoleList = adminService.getUserRole(userRoleGetRequest);
        List<RoleVO> roleVOList;
        roleVOList = new ArrayList<>();
        for (Role userRole : userRoleList) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(userRole, roleVO);
            roleVOList.add(roleVO);
        }
        return ResultUtils.success(roleVOList);

    }

    /**
     * 给角色添加权限
     * @param rolePermissionAddRequest
     * @return
     */
    @PostMapping("/link/rp/add")
    @AuthCheck(permissionName = "给角色添加权限", requirePermission = "admin:addRolePermission")
    public BaseResponse<Boolean> addRolePermission(@RequestBody RolePermissionAddRequest rolePermissionAddRequest) {
        if (rolePermissionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (rolePermissionAddRequest.getRoleId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Boolean b = adminService.addRolePermission(rolePermissionAddRequest);

        return ResultUtils.success(b);
    }

    /**
     * 删除角色被分配到的某些权限
     * @param rolePermissionDeleteRequest
     * @return
     */
    @PostMapping("/link/rp/delete")
    @AuthCheck(permissionName = "删除角色所拥有的部分权限", requirePermission = "admin:deleteRolePermission")
    public BaseResponse<Boolean> deleteRolePermission(@RequestBody RolePermissionDeleteRequest rolePermissionDeleteRequest) {
        if (rolePermissionDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (rolePermissionDeleteRequest.getRoleId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Boolean b = adminService.deleteRolePermission(rolePermissionDeleteRequest);
        return ResultUtils.success(b);
    }


    /**
     * 获取角色所拥有的所有权限
     * @param rolePermissionGetRequest
     * @return
     */
    @PostMapping("/link/rp/selectRolePermission")
    @AuthCheck(permissionName = "获取角色所拥有的所有权限", requirePermission = "admin:selectRolePermission")
    public BaseResponse<List<PermissionVO>> selectRolePermission(@RequestBody RolePermissionGetRequest rolePermissionGetRequest) {
        if (rolePermissionGetRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (rolePermissionGetRequest.getRoleId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Permission> rolePermission = adminService.getRolePermission(rolePermissionGetRequest);
        List<PermissionVO> list = new ArrayList<>();
        for (Permission permission : rolePermission) {
            if (permission != null) {
                PermissionVO permissionVO = new PermissionVO();
                BeanUtils.copyProperties(permission, permissionVO);
                list.add(permissionVO);
            }

        }
        return ResultUtils.success(list);
    }
}
