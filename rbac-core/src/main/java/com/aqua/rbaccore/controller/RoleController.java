package com.aqua.rbaccore.controller;

import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.model.dto.role.RoleAddRequest;
import com.aqua.rbaccore.model.dto.role.RoleQueryRequest;
import com.aqua.rbaccore.model.dto.role.RoleUpdateRequest;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.vo.RoleVO;
import com.aqua.rbaccore.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/7
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     * @param roleAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加角色权限", requirePermission = "role:add")
    public BaseResponse<Long> addRole(@RequestBody RoleAddRequest roleAddRequest) {
        if (roleAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleAddRequest, role);
        boolean result = roleService.save(role);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(role.getId());
    }

    /**
     * 删除角色
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除角色权限", requirePermission = "role:delete")
    public BaseResponse<Boolean> deleteRole(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = roleService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新角色
     * @param roleUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新角色权限", requirePermission = "role:update")
    public BaseResponse<Boolean> updateRole(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        if (roleUpdateRequest == null || roleUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdateRequest, role);
        boolean result = roleService.updateById(role);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取角色信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个角色信息权限", requirePermission = "role:selectOne")
    public BaseResponse<RoleVO> getRoleById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Role role = roleService.getById(id);
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        return ResultUtils.success(roleVO);
    }

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取角色列表权限", requirePermission = "role:selectUserList")
    public BaseResponse<List<RoleVO>> listRole() {
        Role roleQuery = new Role();
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(roleQuery);
        List<Role> roleList = roleService.list(queryWrapper);
        List<RoleVO> roleVOList = roleList.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(roleVOList);
    }

    /**
     * 分页查询角色
     * @param roleQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询角色权限", requirePermission = "role:selectByPage")
    public BaseResponse<Page<RoleVO>> listUserByPage(RoleQueryRequest roleQueryRequest) {
        long current = 1;
        long size = 5;
        Role roleQuery = new Role();
        if (roleQueryRequest != null) {
            BeanUtils.copyProperties(roleQueryRequest, roleQuery);
            current = roleQueryRequest.getCurrent();
            size = roleQueryRequest.getPageSize();
        }
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(roleQuery);
        Page<Role> rolePage = roleService.page(new Page<>(current, size), queryWrapper);
        Page<RoleVO> roleVOPage = new PageDTO<>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal());
        List<RoleVO> roleVOList = rolePage.getRecords().stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        roleVOPage.setRecords(roleVOList);
        return ResultUtils.success(roleVOPage);
    }
}
