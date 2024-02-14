package com.aqua.rbacbusiness.model.dto.firemaintenance;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class FireMaintenanceUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String maintenanceType;

    private String maintenanceDesc;

    private String maintenancePerson;

    private String maintenanceResult;
}
