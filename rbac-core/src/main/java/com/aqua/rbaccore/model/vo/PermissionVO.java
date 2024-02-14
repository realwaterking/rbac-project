package com.aqua.rbaccore.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class PermissionVO {

    private static final long serialVersionUID = 3191241716373113L;

    private Long id;

    private String permissionName;

    private String permissionDesc;

    private String requiredPermission;

    private Date createTime;

    private Date updateTime;
}
