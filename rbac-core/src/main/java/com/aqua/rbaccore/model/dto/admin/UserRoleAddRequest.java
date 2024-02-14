package com.aqua.rbaccore.model.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/12
 */
@Data
public class UserRoleAddRequest implements Serializable {

    private static final long SerialVersionUID = 46454564326464L;

    private Long userId;

    private Long roleId;
}
