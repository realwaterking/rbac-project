package com.aqua.rbacbusiness.serivce;

import com.aqua.rbacbusiness.model.entity.FireData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 70742
* @description 针对表【fire_facility_data(消防设备产生报警数据记录表)】的数据库操作Service
* @createDate 2024-02-08 16:57:31
*/
public interface FireDataService extends IService<FireData> {

    List<FireData> listTopInvokeFireData(int limit);

    Map<String, Long> getAlarmStatistics();

    void load();
}
