package com.aqua.rbacbusiness.mapper;


import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 70742
* @description 针对表【fire_facility(消防设备信息表)】的数据库操作Mapper
* @createDate 2024-02-05 23:25:57
* @Entity com.aqua.rbacbusiness.model.entity.FireFacility
*/
public interface FireFacilityMapper extends BaseMapper<FireFacility> {

    Long getFacilityCount(@Param("startDate") Date startDate);

    Long getIsActive(@Param("id") Long id);
}




