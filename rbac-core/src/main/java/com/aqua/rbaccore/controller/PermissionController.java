package com.aqua.rbaccore.controller;


import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.model.dto.permission.PermissionAddRequest;
import com.aqua.rbaccore.model.dto.permission.PermissionQueryRequest;
import com.aqua.rbaccore.model.dto.permission.PermissionUpdateRequest;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.model.vo.PermissionVO;
import com.aqua.rbaccore.service.PermissionService;
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
@RequestMapping("/permission")
@Slf4j
public class PermissionController {


    @Autowired
    private PermissionService permissionService;

    /**
     * 添加权限
     * @param permissionAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加权限的权限", requirePermission = "permission:access")
    public BaseResponse<Long> addPermission(@RequestBody PermissionAddRequest permissionAddRequest) {
        if (permissionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionAddRequest, permission);
        boolean result = permissionService.save(permission);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(permission.getId());
    }

    /**
     * 删除权限
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除权限的权限", requirePermission = "permission:delete")
    public BaseResponse<Boolean> deletePermission(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = permissionService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新权限
     * @param permissionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新权限的权限", requirePermission = "permission:update")
    public BaseResponse<Boolean> updatePermission(@RequestBody PermissionUpdateRequest permissionUpdateRequest) {
        if (permissionUpdateRequest == null || permissionUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionUpdateRequest, permission);
        boolean result = permissionService.updateById(permission);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取权限信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个权限信息的权限", requirePermission = "permission:selectOne")
    public BaseResponse<PermissionVO> getPermissionById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Permission permission = permissionService.getById(id);
        PermissionVO permissionVO = new PermissionVO();
        BeanUtils.copyProperties(permission, permissionVO);
        return ResultUtils.success(permissionVO);
    }

    /**
     * 获取权限列表
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取所有权限的权限", requirePermission = "permission:selectList")
    public BaseResponse<List<PermissionVO>> listPermission() {
        Permission permissionQuery = new Permission();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>(permissionQuery);
        List<Permission> permissionList = permissionService.list(queryWrapper);
        List<PermissionVO> permissionVOList = permissionList.stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            return permissionVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(permissionVOList);
    }

    /**
     * 分页查询权限
     * @param permissionQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询权限的权限", requirePermission = "permission:selectByPage")
    public BaseResponse<Page<PermissionVO>> listPermissionByPage(PermissionQueryRequest permissionQueryRequest) {
        long current = 1;
        long size = 5;
        Permission permissionQuery = new Permission();
        if (permissionQueryRequest != null) {
            BeanUtils.copyProperties(permissionQueryRequest, permissionQuery);
            current = permissionQueryRequest.getCurrent();
            size = permissionQueryRequest.getPageSize();
        }
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>(permissionQuery);
        Page<Permission> permissionPage = permissionService.page(new Page<>(current, size), queryWrapper);
        Page<PermissionVO> permissionVOPage = new PageDTO<>(permissionPage.getCurrent(), permissionPage.getSize(), permissionPage.getTotal());
        List<PermissionVO> permissionVOList = permissionPage.getRecords().stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            return permissionVO;
        }).collect(Collectors.toList());
        permissionVOPage.setRecords(permissionVOList);
        return ResultUtils.success(permissionVOPage);
    }

    @PostMapping("/load")
    public BaseResponse<String> load() {
        permissionService.load();
        return ResultUtils.success("ok");
    }
}
