package com.aqua.rbacbusiness.model.dto.firefacility;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/7
 */
@Data
public class FireFacilityAddRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 消防设备名称
     */
    private String facilityName;

    /**
     * 消防设备工作位置
     */
    private String location;

    /**
     * 消防设备类型
     */
    private String type;

    /**
     * 消防设备制造商
     */
    private String manufacturer;

    /**
     * 消防设备型号
     */
    private String model;

    /**
     * 消防设备容积
     */
    private int capacity;

}
