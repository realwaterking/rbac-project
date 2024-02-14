package com.aqua.rbacbusiness.model.dto.firefacility;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class FireFacilityUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String facilityName;

    private String location;

    private String type;

    private String manufacturer;

    private String model;

    private int capacity;

    private boolean isActive;
}
