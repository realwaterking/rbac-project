package com.aqua.rbacbusiness.mapper;

import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
* @author 70742
* @description 针对表【fire_maintenance(消防设备日常维护记录表)】的数据库操作Mapper
* @createDate 2024-02-08 23:19:38
* @Entity com.aqua.rbacbusiness.model.entity.FireMaintenance
*/
public interface FireMaintenanceMapper extends BaseMapper<FireMaintenance> {

    int getMaintenanceCount(@Param("startDate") Date startDate);

    String getFireMaintenanceById(@Param("id") Long id);

    String getFireMaintenanceDate(@Param("id") Long id);
}




