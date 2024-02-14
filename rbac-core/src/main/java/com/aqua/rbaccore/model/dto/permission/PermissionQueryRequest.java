package com.aqua.rbaccore.model.dto.permission;

import com.aqua.rbaccommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String permissionName;

    private String permissionDesc;

    private Date createTime;

    private Date updateTime;
}
