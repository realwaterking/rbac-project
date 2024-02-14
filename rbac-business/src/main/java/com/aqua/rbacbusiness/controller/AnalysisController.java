package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.mapper.FireDataMapper;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.DataInfoVO;
import com.aqua.rbacbusiness.model.vo.FireDataVO;
import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

/**
 * @author water king
 * @time 2024/2/8
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Autowired
    private FireDataMapper fireDataMapper;

    @Autowired
    private FireDataService fireDataService;

    @Autowired
    private FireMaintenanceService fireMaintenanceService;

    @Autowired
    private FireFacilityService fireFacilityService;

    /**
     * 获取报警频率前三的设备信息
     *
     * @return
     */
    @GetMapping("/top/data/invoke")
    @AuthCheck(permissionName = "查看报警频率前三的设备", requirePermission = "invoke:top3")
    public BaseResponse<List<DataInfoVO>> listTopInvokeFireData() {
        List<FireData> fireDataList = fireDataService.listTopInvokeFireData(3);
        List<DataInfoVO> list = new ArrayList<>();
        for (FireData fireData : fireDataList) {
            if (fireData != null) {
                DataInfoVO dataInfoVO = new DataInfoVO();
                BeanUtils.copyProperties(fireData, dataInfoVO);
                list.add(dataInfoVO);
            }
        }
        return ResultUtils.success(list);
    }

    /**
     * 获取最近五条报警信息
     *
     * @return
     */
    @GetMapping("/top/data/list")
    @AuthCheck(permissionName = "获取最近报警信息", requirePermission = "invoke:recent5")
    public BaseResponse<List<FireDataVO>> listTopInvokeDetailData() {
        // 修改查询条件，按时间降序排列，限制结果为前五条数据
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("alarmTime").last("LIMIT 5");
        List<FireData> list = fireDataService.list(queryWrapper);
        List<FireDataVO> fireDataVOList = new ArrayList<>();
        for (FireData fireData : list) {
            if (fireData != null) {
                FireDataVO fireDataVO = new FireDataVO();
                BeanUtils.copyProperties(fireData, fireDataVO);
                fireDataVOList.add(fireDataVO);
            }
        }

        return ResultUtils.success(fireDataVOList);
    }

    /**
     * 获取连接状态统计信息
     *
     * @return
     */
    @GetMapping("/connection/status/count")
    @AuthCheck(permissionName = "获取连接状态统计信息", requirePermission = "invoke:connectionStatusCount")
    public BaseResponse<Map<String, Integer>> getConnectionStatusCount() {
        // 查询所有设备数据
        List<FireData> fireDataList = fireDataService.list();

        // 初始化统计数据
        int normalCount = 0;
        int unstableCount = 0;
        int failedCount = 0;

        // 遍历设备数据，统计连接状态
        for (FireData fireData : fireDataList) {
            int connectionStatus = fireData.getConnectionStatus();
            if (connectionStatus == 0) {
                normalCount++;
            } else if (connectionStatus == 1) {
                unstableCount++;
            } else if (connectionStatus == 2) {
                failedCount++;
            }
            // 如果连接状态无效，不做任何处理
        }

        // 将统计结果放入Map中
        Map<String, Integer> statusCount = new HashMap<>();
        statusCount.put("normalCount", normalCount);
        statusCount.put("unstableCount", unstableCount);
        statusCount.put("failedCount", failedCount);

        return ResultUtils.success(statusCount);
    }

    /**
     * 获取近一个月内维护过和未维护过的设备数量
     *
     * @return
     */
    @GetMapping("/maintenance/count")
    @AuthCheck(permissionName = "获取近一个月维护信息统计", requirePermission = "invoke:recentMaintenanceCount")
    public BaseResponse<Map<String, Long>> getMaintenanceCount() {
        // 计算最近一个月的起始日期
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Date startDate = java.sql.Date.valueOf(oneMonthAgo);

        // 查询近一个月维护过的设备数量
        QueryWrapper<FireMaintenance> maintenanceQuery = new QueryWrapper<>();
        maintenanceQuery.ge("maintenanceDate", startDate);
        Long maintainedCount = fireMaintenanceService.count(maintenanceQuery);

        // 查询设备总数
        Long totalDeviceCount = fireFacilityService.count();

        // 计算近一个月未维护过的设备数量
        Long notMaintainedCount = totalDeviceCount - maintainedCount;

        // 将结果放入Map中
        Map<String, Long> result = new HashMap<>();
        result.put("maintainedCount", maintainedCount);
        result.put("notMaintainedCount", notMaintainedCount);

        return ResultUtils.success(result);
    }


}
