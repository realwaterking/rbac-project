package com.aqua.rbaccore.model.dto.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/12
 */
@Data
public class RolePermissionAddRequest implements Serializable {

    private static final long serialVersionUID = 789456164566L;

    private Long roleId;

    private List<Long> permissionId;
}
