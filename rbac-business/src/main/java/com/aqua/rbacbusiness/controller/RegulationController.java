package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityQueryRequest;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.vo.FireFacilityVO;
import com.aqua.rbacbusiness.serivce.DispatchDataService;
import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/regulation")
@Slf4j
public class RegulationController {

    @Autowired
    private FireFacilityService fireFacilityService;

    @Autowired
    private FireDataService fireDataService;

    @Autowired
    private DispatchDataService dispatchDataService;

    /**
     * 分页查询消防设备信息
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防设备信息的权限", requirePermission = "regulation:selectByPage")
    public BaseResponse<Page<FireFacilityVO>> listFireFacilityByPage(FireFacilityQueryRequest fireFacilityQueryRequest) {
        long current = 1;
        long size = 10;
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
     * 查询报警设备数和未报警设备数
     * @return
     */
    @GetMapping("/regulation/fire/data")
    @AuthCheck(permissionName = "查询报警设备数", requirePermission = "regulation:getFireData")
    public BaseResponse<Map<String, Long>> getRegulationFireData() {
        Map<String, Long> alarmStatistics = fireDataService.getAlarmStatistics();
        return ResultUtils.success(alarmStatistics);
    }

    /**
     * 获取出警情况统计
     * @return
     */
    @GetMapping("/call/situation")
    @AuthCheck(permissionName = "获取出警情况", requirePermission = "regulation:callSituation")
    public BaseResponse<Map<String, Long>> getCallSituation() {
        // 计算出警情况统计
        Map<String, Long> callSituation = new HashMap<>();
        callSituation.put("totalDispatch", dispatchDataService.getTotalDispatch());
        callSituation.put("successfulDispatch", dispatchDataService.getSuccessfulDispatch());
        callSituation.put("failedDispatch", dispatchDataService.getFailedDispatch());
        return ResultUtils.success(callSituation);
    }

}
