package com.aqua.rbaccore.mapper;

import com.aqua.rbaccore.model.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Mapper
* @createDate 2024-02-05 23:26:00
* @Entity com.aqua.rbaccore.model.entity.Permission
*/
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermissions();
}




