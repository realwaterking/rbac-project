package com.aqua.rbaccore.model.dto.user;

import com.aqua.rbaccommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/6
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String username;

    private String userAccount;

    private String phoneNumber;

    private String location;

    private Date createTime;

    private Date updateTime;
}
