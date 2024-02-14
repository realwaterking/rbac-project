package com.aqua.rbaccore.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/6
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
