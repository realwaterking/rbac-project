package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class FireMaintenanceVO implements Serializable {

    private static final long serialVersionUID = 1625468943646L;

    private Long id;

    private Long deviceId;

    private String maintenanceType;

    private String maintenanceDesc;

    private String maintenancePerson;

    private String maintenanceResult;

    private Date maintenanceDate;
}
