package com.aqua.rbaccore.model.dto.role;

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
public class RoleQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;
}
