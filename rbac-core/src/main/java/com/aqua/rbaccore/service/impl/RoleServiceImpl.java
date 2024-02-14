package com.aqua.rbaccore.service.impl;

import com.aqua.rbaccore.mapper.RoleMapper;
import com.aqua.rbaccore.mapper.UserRoleMapper;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.entity.UserRole;
import com.aqua.rbaccore.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 70742
* @description 针对表【role(角色信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:04
*/
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleList(Long userId) {
        try {
            List<Role> roleList = new ArrayList<>();
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userId", userId);
            List<UserRole> userRoleList = userRoleMapper.selectList(queryWrapper);
            for (UserRole userRole : userRoleList) {
                QueryWrapper<Role> wrapper = new QueryWrapper<>();
                wrapper.eq("id", userRole.getRoleId()); // 使用 userRole 的信息构建查询条件
                Role role = roleMapper.selectOne(wrapper);
                if (role != null) { // 添加 null 检查
                    roleList.add(role); // 添加角色到 roleList
                }
            }
            return roleList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}




