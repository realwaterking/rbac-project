package com.aqua.rbacbusiness.model.dto.firefacility;

import com.aqua.rbaccommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FireFacilityQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

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
