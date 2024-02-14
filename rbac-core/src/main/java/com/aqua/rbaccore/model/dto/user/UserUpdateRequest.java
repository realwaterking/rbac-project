package com.aqua.rbaccore.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/6
 */
@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String username;

    private String userPassword;

    private String phoneNumber;

    private String location;
}
