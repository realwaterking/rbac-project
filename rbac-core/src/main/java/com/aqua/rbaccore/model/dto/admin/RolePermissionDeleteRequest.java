package com.aqua.rbaccore.model.dto.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/12
 */
@Data
public class RolePermissionDeleteRequest implements Serializable {

    private static final long serialVersionUID = 4561234612642L;

    private Long roleId;

    private List<Long> permissionId;
}
