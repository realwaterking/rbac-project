package com.aqua.rbacbusiness.serivce;

import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.vo.FireFacilityInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 70742
* @description 针对表【fire_facility(消防设备信息表)】的数据库操作Service
* @createDate 2024-02-05 23:25:57
*/
public interface FireFacilityService extends IService<FireFacility> {

    List<FireFacilityInfoVO> getFireFacilities(String location);
}
