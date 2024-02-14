package com.aqua.rbaccore.model.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/12
 */
@Data
public class UserRoleGetRequest implements Serializable {

    private static final long serialVersionUID = 7895231654321L;

    private Long userId;
}
