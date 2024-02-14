package com.aqua.rbaccore.model.dto.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class RoleUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String roleName;

    private String roleDesc;
}
