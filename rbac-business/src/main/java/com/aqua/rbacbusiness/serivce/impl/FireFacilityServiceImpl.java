package com.aqua.rbacbusiness.serivce.impl;

import com.aqua.rbacbusiness.mapper.FireFacilityMapper;
import com.aqua.rbacbusiness.mapper.FireMaintenanceMapper;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.vo.FireFacilityInfoVO;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 70742
* @description 针对表【fire_facility(消防设备信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:25:57
*/
@Service
@Slf4j
public class FireFacilityServiceImpl extends ServiceImpl<FireFacilityMapper, FireFacility>
    implements FireFacilityService {

    @Resource
    private FireFacilityMapper fireFacilityMapper;

    @Resource
    private FireMaintenanceMapper fireMaintenanceMapper;

    @Override
    public List<FireFacilityInfoVO> getFireFacilities(String location) {
        String likeUserLocation = "%" + location + "%";
        QueryWrapper<FireFacility> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("location", likeUserLocation);
        List<FireFacility> fireFacilityList = fireFacilityMapper.selectList(queryWrapper);

        // 创建用于存储结果的列表
        List<FireFacilityInfoVO> result = new ArrayList<>();

        // 使用 foreach 循环遍历 fireFacilityList 中的每个 FireFacility 对象
        for (FireFacility fireFacility : fireFacilityList) {
            FireFacilityInfoVO fireFacilityInfoVO = new FireFacilityInfoVO();
            BeanUtils.copyProperties(fireFacility, fireFacilityInfoVO);
            // 获取每个设备的最近一次维护人员信息
            String maintenancePerson = fireMaintenanceMapper.getFireMaintenanceById(fireFacility.getId());
            String maintenanceDate = fireMaintenanceMapper.getFireMaintenanceDate(fireFacility.getId());
            Long isActive = fireFacilityMapper.getIsActive(fireFacility.getId());
            fireFacilityInfoVO.setIsActive(isActive);
            fireFacilityInfoVO.setMaintenanceDate(maintenanceDate);
            fireFacilityInfoVO.setMaintenancePerson(maintenancePerson);
            // 将处理后的 FireFacilityInfoVO 对象添加到结果列表中
            result.add(fireFacilityInfoVO);
        }

        // 返回结果列表
        return result;
    }


}




