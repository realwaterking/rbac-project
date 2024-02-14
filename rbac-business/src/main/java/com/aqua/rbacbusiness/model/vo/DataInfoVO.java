package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class DataInfoVO implements Serializable {

    private static final long serialVersionUID = 789465498724468L;

    private String name;

    private String location;

    private Integer status;

    private Integer totalNum;

}
