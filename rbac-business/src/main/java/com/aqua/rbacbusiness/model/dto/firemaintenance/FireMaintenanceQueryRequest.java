package com.aqua.rbacbusiness.model.dto.firemaintenance;

import com.aqua.rbaccommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FireMaintenanceQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;


}
