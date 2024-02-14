package com.aqua.rbaccore.constant;

/**
 * 角色常量
 * @author water king
 * @time 2024/2/6
 */
public interface RoleConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 系统用户id (虚拟用户)
     */
    long SYSTEM_USER_ID = 0;

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 维保单位角色
     */
    String MAINTENANCE_ROLE = "maintenance";

    /**
     * 企业端角色
     */
    String ENTERPRISE_ROLE = "enterprise";

    /**
     * 监管端角色
     */
    String SUPERVISOR_ROLE = "supervisor";





}
