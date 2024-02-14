package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.vo.FireFacilityInfoVO;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/client")
@Slf4j
public class ClientController {

    @Autowired
    private FireFacilityService fireFacilityService;


    /**
     * 根据用户所在地查询消防设备信息以及最近一次维护时间
     * @param location 用户所在地
     * @return 消防设备信息列表
     */
    @GetMapping("/fireFacilities")
    public BaseResponse<List<FireFacilityInfoVO>> getFireFacilitiesByLocation(@RequestParam String location) {
        // 根据用户地址查询消防设备
        List<FireFacilityInfoVO> fireFacilities = fireFacilityService.getFireFacilities(location);

        return ResultUtils.success(fireFacilities);
    }
}
