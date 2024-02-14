package com.aqua.rbaccore.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 3191241716373113L;

    private Long id;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;

}
