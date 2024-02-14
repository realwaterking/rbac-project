package com.aqua.rbacbusiness.serivce.impl;


import com.aqua.rbacbusiness.mapper.FireDataMapper;
import com.aqua.rbacbusiness.mapper.FireFacilityMapper;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.mapper.PermissionMapper;
import com.aqua.rbaccore.model.entity.Permission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 70742
* @description 针对表【fire_facility_data(消防设备产生报警数据记录表)】的数据库操作Service实现
* @createDate 2024-02-08 16:57:31
*/
@Service
public class FireDataServiceImpl extends ServiceImpl<FireDataMapper, FireData>
    implements FireDataService {

    @Resource
    private FireDataMapper fireDataMapper;

    @Resource
    private FireFacilityMapper fireFacilityMapper;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<FireData> listTopInvokeFireData(int limit) {
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("deviceId").orderByDesc("count(deviceId)").last("LIMIT 3");
        List<FireData> fireDataList = fireDataMapper.selectList(queryWrapper);
        return fireDataList;
    }

    /**
     * 计算报警设备数和未报警设备数
     * @return
     */
    @Override
    public Map<String, Long> getAlarmStatistics() {
        Map<String, Long> statisticsMap = new HashMap<>();

        // 查询发生过报警的设备数量
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT device_id");
        Long numDevicesWithAlarm = fireDataMapper.selectCount(queryWrapper);
        statisticsMap.put("num_devices_with_alarm", numDevicesWithAlarm);

        // 查询总设备数量
        QueryWrapper<FireFacility> queryWrapper1 = new QueryWrapper<>();
        Long totalDevices = fireFacilityMapper.selectCount(queryWrapper1);
        // 计算没有发生过报警的设备数量
        Long numDevicesWithoutAlarm = totalDevices - numDevicesWithAlarm;
        statisticsMap.put("num_devices_without_alarm", numDevicesWithoutAlarm);

        return statisticsMap;
    }

    @Override
    public void load() {
        List<String> permissions = permissionMapper.selectPermissions();
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RestController.class);
        Collection<Object> controllers = map.values();
        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                AuthCheck annotation = method.getAnnotation(AuthCheck.class);
                System.out.println(annotation);
                if (annotation != null) {
                    String permissionName = annotation.permissionName();
                    String requirePermission = annotation.requirePermission();

                    if (permissions.contains(requirePermission)) {
                        Permission permission = new Permission();
                        permission.setPermissionName(permissionName);
                        permission.setRequiredPermission(requirePermission);
                        permissionMapper.insert(permission);
                    }
                }
            }
        }
    }
}




