package com.aqua.rbacbusiness.model.dto.firedata;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class FireDataAddRequest implements Serializable {

    private static final long serialVersionUID = 31912410793L;

    private float temperature;

    private float humidity;

    private float smokeConcentration;

    private float gasConcentration;

    private Integer illumination;

    private String deviceStatus;

    private Long deviceId;

    private int batteryLever;

    private int connectionStatus;

    private String alarmType;

    private String alarmLocation;
}
