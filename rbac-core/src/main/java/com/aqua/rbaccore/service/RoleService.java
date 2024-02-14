package com.aqua.rbaccore.service;

import com.aqua.rbaccore.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 70742
* @description 针对表【role(角色信息表)】的数据库操作Service
* @createDate 2024-02-05 23:26:04
*/
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id获取角色列表
     * @return
     */
    List<Role> getRoleList(Long userId);
}
