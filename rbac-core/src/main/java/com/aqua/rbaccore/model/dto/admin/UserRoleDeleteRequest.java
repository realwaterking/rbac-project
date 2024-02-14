package com.aqua.rbaccore.model.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/12
 */
@Data
public class UserRoleDeleteRequest implements Serializable {

    private static final long serialVersionUID = 494579234565L;

    private Long userId;
}
