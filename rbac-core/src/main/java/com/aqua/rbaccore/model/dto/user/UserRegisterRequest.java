package com.aqua.rbaccore.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/6
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String username;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String phoneNumber;

    private String location;
}
