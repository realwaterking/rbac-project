package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.dispatchdata.DispatchDataQueryRequest;
import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceAddRequest;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.FireDataVO;
import com.aqua.rbacbusiness.model.vo.FireFacilityVO;
import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/maintenance")
@Slf4j
public class MaintenanceController {

    @Autowired
    private FireMaintenanceService fireMaintenanceService;

    @Autowired
    private FireFacilityService fireFacilityService;

    @Autowired
    private FireDataService fireDataService;

    /**
     * 添加日常维护记录
     * @param fireMaintenanceAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加日常维护记录的权限", requirePermission = "fireMaintenance:add")
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
     * 分页查询消防设备信息
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防设备信息的权限", requirePermission = "fireFacility:selectByPage")
    public BaseResponse<Page<FireFacilityVO>> listFireFacilityByPage(DispatchDataQueryRequest dispatchDataQueryRequest) {
        long current = 1;
        long size = 5;
        if (dispatchDataQueryRequest != null) {
            current = dispatchDataQueryRequest.getCurrent();
            size = dispatchDataQueryRequest.getPageSize();
        }

        // 构建查询条件
        QueryWrapper<FireFacility> queryWrapper = new QueryWrapper<>();
        // 这里根据实际情况构建查询条件，示例中假设 dispatchDataQueryRequest 中包含了需要查询的条件
        if (dispatchDataQueryRequest != null) {
            // 设置其他查询条件...
        }

        // 执行分页查询
        Page<FireFacility> fireFacilityPage = new Page<>(current, size);
        Page<FireFacility> pageResult = fireFacilityService.page(fireFacilityPage, queryWrapper);

        // 转换结果为 VO 对象
        List<FireFacilityVO> fireFacilityVOList = pageResult.getRecords().stream().map(fireFacility -> {
            FireFacilityVO fireFacilityVO = new FireFacilityVO();
            BeanUtils.copyProperties(fireFacility, fireFacilityVO);
            return fireFacilityVO;
        }).collect(Collectors.toList());

        // 构建返回结果
        Page<FireFacilityVO> fireFacilityVOPage = new Page<>();
        BeanUtils.copyProperties(pageResult, fireFacilityVOPage);
        fireFacilityVOPage.setRecords(fireFacilityVOList);

        return ResultUtils.success(fireFacilityVOPage);
    }

    /**
     * 获取最近五条报警信息
     * @return
     */
    @GetMapping("/top/data/list")
    @AuthCheck(permissionName = "获取最近报警信息", requirePermission = "invoke:recent5")
    public BaseResponse<List<FireDataVO>> listTopInvokeDetailData() {
        // 修改查询条件，按时间降序排列，限制结果为前五条数据
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("alarmTime").last("LIMIT 5");
        List<FireData> list = fireDataService.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<FireDataVO> fireDataVOList = list.stream().map(fireData -> {
            FireDataVO fireDataVO = new FireDataVO();
            BeanUtils.copyProperties(fireData, fireDataVO);
            return fireDataVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(fireDataVOList);
    }

   // TODO (aqua 2024/2/11 0:13) 差一个接收消防设施报警信息的方法

}
