package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityQueryRequest;
import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceQueryRequest;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.FireFacilityVO;
import com.aqua.rbacbusiness.model.vo.FireMaintenanceVO;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/enterprise")
@Slf4j
public class EnterpriseController {

    @Autowired
    private FireFacilityService fireFacilityService;

    @Autowired
    private FireMaintenanceService fireMaintenanceService;

    /**
     * 分页查询消防设备状态等信息
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list/facility/page")
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

    /**
     * 分页查询消防设备维护信息
     * @param fireMaintenanceQueryRequest
     * @return
     */
    @GetMapping("/list/maintenance/page")
    @AuthCheck(permissionName = "分页查询消防设备维护信息的权限", requirePermission = "fireMaintenance:selectByPage")
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
