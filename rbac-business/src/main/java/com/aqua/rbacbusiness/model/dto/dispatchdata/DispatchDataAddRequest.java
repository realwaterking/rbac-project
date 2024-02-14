package com.aqua.rbacbusiness.model.dto.dispatchdata;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/11
 */
@Data
public class DispatchDataAddRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long fireDataId;

    private Long userId;

    private String result;
}
