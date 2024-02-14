package com.aqua.rbacbusiness.model.dto.dispatchdata;

import com.aqua.rbaccommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DispatchDataQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private Long fireDataId;

    private Long userId;
}
