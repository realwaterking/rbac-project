package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceAddRequest;
import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceQueryRequest;
import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceUpdateRequest;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.FireMaintenanceVO;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
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
 * @time 2024/2/8
 */
@RestController
@RequestMapping("/maintenance")
@Slf4j
public class FireMaintenanceController {

    @Autowired
    private FireMaintenanceService fireMaintenanceService;

    /**
     * 添加日常维护记录
     * @param fireMaintenanceAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加日常维护记录的权限", requirePermission = "admin:fireMaintenance:add")
    public BaseResponse<Long> addFireMaintenance(@RequestBody FireMaintenanceAddRequest fireMaintenanceAddRequest) {
        if (fireMaintenanceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireMaintenance fireMaintenance = new FireMaintenance();
        BeanUtils.copyProperties(fireMaintenanceAddRequest, fireMaintenance);
        boolean result = fireMaintenanceService.save(fireMaintenance);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(fireMaintenance.getId());
    }

    /**
     * 删除日常维护记录数据
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(permissionName = "删除日常维护记录数据的权限", requirePermission = "admin:fireMaintenance:delete")
    public BaseResponse<Boolean> deleteFireMaintenance(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = fireMaintenanceService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新日常维护记录数据
     * @param fireMaintenanceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(permissionName = "更新日常维护记录的权限", requirePermission = "admin:fireMaintenance:update")
    public BaseResponse<Boolean> updateFireMaintenance(@RequestBody FireMaintenanceUpdateRequest fireMaintenanceUpdateRequest) {
        if (fireMaintenanceUpdateRequest == null || fireMaintenanceUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireMaintenance fireFacilityData = new FireMaintenance();
        BeanUtils.copyProperties(fireMaintenanceUpdateRequest, fireFacilityData);
        boolean result = fireMaintenanceService.updateById(fireFacilityData);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取单个消防设备维护信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @AuthCheck(permissionName = "获取单个消防设备维护信息的权限", requirePermission = "admin:fireMaintenance:selectOne")
    public BaseResponse<FireMaintenanceVO> getFireMaintenanceById(@PathVariable int id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireMaintenance fireMaintenance = fireMaintenanceService.getById(id);
        FireMaintenanceVO fireMaintenanceVO = new FireMaintenanceVO();
        BeanUtils.copyProperties(fireMaintenance, fireMaintenanceVO);
        return ResultUtils.success(fireMaintenanceVO);
    }

    /**
     * 获取消防设备维护信息列表
     * @return
     */
    @GetMapping("/list")
    @AuthCheck(permissionName = "获取消防设备维护信息列表", requirePermission = "admin:fireMaintenance:list")
    public BaseResponse<List<FireMaintenanceVO>> listFireMaintenance() {
        FireMaintenance fireMaintenanceQuery = new FireMaintenance();
        QueryWrapper<FireMaintenance> queryWrapper = new QueryWrapper<>(fireMaintenanceQuery);
        List<FireMaintenance> fireMaintenanceList = fireMaintenanceService.list(queryWrapper);
        List<FireMaintenanceVO> fireMaintenanceVOList = fireMaintenanceList.stream().map(fireMaintenance -> {
            FireMaintenanceVO fireMaintenanceVO = new FireMaintenanceVO();
            BeanUtils.copyProperties(fireMaintenance, fireMaintenanceVO);
            return fireMaintenanceVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(fireMaintenanceVOList);
    }

    /**
     * 分页查询消防设备维护信息
     * @param fireMaintenanceQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防设备维护信息的权限", requirePermission = "admin:fireMaintenance:selectByPage")
    public BaseResponse<Page<FireMaintenanceVO>> listFireMaintenanceByPage(FireMaintenanceQueryRequest fireMaintenanceQueryRequest) {
        long current = 1;
        long size = 5;
        FireMaintenance fireMaintenanceQuery = new FireMaintenance();
        if (fireMaintenanceQueryRequest != null) {
            BeanUtils.copyProperties(fireMaintenanceQueryRequest, fireMaintenanceQuery);
            current = fireMaintenanceQueryRequest.getCurrent();
            size = fireMaintenanceQueryRequest.getPageSize();
        }
        QueryWrapper<FireMaintenance> queryWrapper = new QueryWrapper<>(fireMaintenanceQuery);
        Page<FireMaintenance> fireMaintenancePage = fireMaintenanceService.page(new Page<>(current, size), queryWrapper);
        Page<FireMaintenanceVO> fireMaintenanceVOPage = new PageDTO<>(fireMaintenancePage.getCurrent(), fireMaintenancePage.getSize(), fireMaintenancePage.getTotal());
        List<FireMaintenanceVO> fireMaintenanceVOList = fireMaintenancePage.getRecords().stream().map(fireMaintenance -> {
            FireMaintenanceVO fireMaintenanceVO = new FireMaintenanceVO();
            BeanUtils.copyProperties(fireMaintenance, fireMaintenanceVO);
            return fireMaintenanceVO;
        }).collect(Collectors.toList());
        fireMaintenanceVOPage.setRecords(fireMaintenanceVOList);
        return ResultUtils.success(fireMaintenanceVOPage);
    }
}
