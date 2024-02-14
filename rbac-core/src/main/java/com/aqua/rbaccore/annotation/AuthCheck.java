package com.aqua.rbaccore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author water king
 * @time 2024/2/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 权限名称
     * @return
     */
    String permissionName() default "";

    /**
     * 必须有某个权限
     *
     * @return
     */
    String requirePermission() default "";
}