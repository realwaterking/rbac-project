package com.aqua.rbaccore.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/6
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 3191241716373113L;

    private Long id;

    private String username;

    private String userAccount;

    private String phoneNumber;

    private String location;

    private Date createTime;

    private Date updateTime;

}
