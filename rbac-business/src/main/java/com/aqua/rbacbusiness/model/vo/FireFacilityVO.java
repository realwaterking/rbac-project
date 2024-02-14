package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class FireFacilityVO implements Serializable {

    private static final long serialVersionUID = 3191241716373113L;

    private Long id;

    private String facilityName;

    private String location;

    private String type;

    private String manufacturer;

    private String model;

    private int capacity;

    private Date installationDate;

    private Date lastInspectionDate;

    private boolean isActive;

    private Date createTime;

    private Date updateTime;
}
