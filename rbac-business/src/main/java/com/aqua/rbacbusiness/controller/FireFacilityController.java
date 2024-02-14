package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityAddRequest;
import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityQueryRequest;
import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityUpdateRequest;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.vo.FireFacilityVO;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.DeleteRequest;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
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
@RequestMapping("/fire")
@Slf4j
public class FireFacilityController {

    @Autowired
    private FireFacilityService fireFacilityService;

    /**
     * 添加消防设备
     * @param fireFacilityAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加消防设备的权限", requirePermission = "fireFacility:add")
    public BaseResponse<Long> addFireFacility(@RequestBody FireFacilityAddRequest fireFacilityAddRequest) {
        if (fireFacilityAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireFacility fireFacility = new FireFacility();
        BeanUtils.copyProperties(fireFacilityAddRequest, fireFacility);
        boolean result = fireFacilityService.save(fireFacility);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(fireFacility.getId());
    }

    /**
     * 删除消防设备
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除消防设备的权限", requirePermission = "fireFacility:delete")
    public BaseResponse<Boolean> deleteFireFacility(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = fireFacilityService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新消防设备信息
     * @param fireFacilityUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新消防设备信息的权限", requirePermission = "fireFacility:update")
    public BaseResponse<Boolean> updateFireFacility(@RequestBody FireFacilityUpdateRequest fireFacilityUpdateRequest) {
        if (fireFacilityUpdateRequest == null || fireFacilityUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireFacility fireFacility = new FireFacility();
        BeanUtils.copyProperties(fireFacilityUpdateRequest, fireFacility);
        boolean result = fireFacilityService.updateById(fireFacility);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取消防设备信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个消防设备信息的权限", requirePermission = "fireFacility:selectOne")
    public BaseResponse<FireFacilityVO> getFireFacilityById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireFacility fireFacility = fireFacilityService.getById(id);
        FireFacilityVO fireFacilityVO = new FireFacilityVO();
        BeanUtils.copyProperties(fireFacility, fireFacilityVO);
        return ResultUtils.success(fireFacilityVO);
    }

    /**
     * 获取消防设备信息列表
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取消防设备信息列表的权限", requirePermission = "fireFacility:selectList")
    public BaseResponse<List<FireFacilityVO>> listFireFacility(FireFacilityQueryRequest fireFacilityQueryRequest) {
        FireFacility fireFacilityQuery = new FireFacility();
        if (fireFacilityQueryRequest != null) {
            BeanUtils.copyProperties(fireFacilityQueryRequest, fireFacilityQuery);
        }
        QueryWrapper<FireFacility> queryWrapper = new QueryWrapper<>(fireFacilityQuery);
        List<FireFacility> fireFacilityList = fireFacilityService.list(queryWrapper);
        List<FireFacilityVO> fireFacilityVOList = fireFacilityList.stream().map(fireFacility -> {
            FireFacilityVO fireFacilityVO = new FireFacilityVO();
            BeanUtils.copyProperties(fireFacility, fireFacilityVO);
            return fireFacilityVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(fireFacilityVOList);
    }

    /**
     * 分页查询消防设备信息
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防设备信息的权限", requirePermission = "fireFacility:selectByPage")
    public BaseResponse<Page<FireFacilityVO>> listFireFacilityByPage(FireFacilityQueryRequest fireFacilityQueryRequest) {
        long current = 1;
        long size = 5;
        FireFacility fireFacilityQuery = new FireFacility();
        if (fireFacilityQueryRequest != null) {
            BeanUtils.copyProperties(fireFacilityQueryRequest, fireFacilityQuery);
            current = fireFacilityQueryRequest.getCurrent();
            size = fireFacilityQueryRequest.getPageSize();
        }
        QueryWrapper<FireFacility> queryWrapper = new QueryWrapper<>(fireFacilityQuery);
        Page<FireFacility> fireFacilityPage = fireFacilityService.page(new Page<>(current, size), queryWrapper);
        Page<FireFacilityVO> fireFacilityVOPage = new PageDTO<>(fireFacilityPage.getCurrent(), fireFacilityPage.getSize(), fireFacilityPage.getTotal());
        List<FireFacilityVO> fireFacilityVOList = fireFacilityPage.getRecords().stream().map(fireFacility -> {
            FireFacilityVO fireFacilityVO = new FireFacilityVO();
            BeanUtils.copyProperties(fireFacility, fireFacilityVO);
            return fireFacilityVO;
        }).collect(Collectors.toList());
        fireFacilityVOPage.setRecords(fireFacilityVOList);
        return ResultUtils.success(fireFacilityVOPage);
    }

}
