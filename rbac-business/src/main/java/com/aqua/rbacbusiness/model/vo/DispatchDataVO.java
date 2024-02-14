package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/11
 */
@Data
public class DispatchDataVO implements Serializable {

    private static final long serialVersionUID = 1625468943646L;

    private Long id;

    private Long fireDataId;

    private Long userId;

    private String result;

    private String createTime;
}
