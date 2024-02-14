package com.aqua.rbaccore.model.dto.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class PermissionAddRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String PermissionName;

    private String PermissionDesc;

    private String requiredPermission;
}
