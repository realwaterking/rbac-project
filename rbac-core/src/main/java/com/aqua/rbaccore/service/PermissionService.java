package com.aqua.rbaccore.service;

import com.aqua.rbaccore.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Service
* @createDate 2024-02-05 23:26:00
*/
public interface PermissionService extends IService<Permission> {

    String load();

    List<String> getPermissionList(Long id);
}
