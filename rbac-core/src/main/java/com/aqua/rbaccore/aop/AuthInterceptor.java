package com.aqua.rbaccore.aop;

import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.aqua.rbaccore.model.entity.Role;
import com.aqua.rbaccore.model.entity.User;
import com.aqua.rbaccore.service.PermissionService;
import com.aqua.rbaccore.service.RoleService;
import com.aqua.rbaccore.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/6
 */
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = userService.getLoginUser(request);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 如果用户是管理员，直接放行
        if ("admin".equals(user.getUserAccount())) {
            return joinPoint.proceed();
        }

        // 获取用户拥有的所有角色
        List<Role> roles = roleService.getRoleList(user.getId());

        // 获取目标方法需要的权限
        String requirePermission = authCheck.requirePermission();

        // 遍历用户的角色，检查是否拥有目标方法所需的权限
        boolean hasPermission = false;
        for (Role role : roles) {
            List<String> permissions = permissionService.getPermissionList(role.getId());
            if (permissions.contains(requirePermission)) {
                hasPermission = true;
                break;
            }
        }

        // 如果用户没有权限，则抛出异常
        if (!hasPermission) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 如果有权限，放行目标方法
        return joinPoint.proceed();
    }

}
