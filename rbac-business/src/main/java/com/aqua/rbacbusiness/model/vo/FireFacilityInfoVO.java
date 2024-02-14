package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/11
 */
@Data
public class FireFacilityInfoVO implements Serializable {

    private static final long serialVersionUID = 1625468943646L;

    private String facilityName;

    private String type;

    private String manufacturer;

    private String location;

    private Long isActive;

    private String maintenancePerson;

    private String maintenanceDate;
}
