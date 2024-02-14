package com.aqua.rbacbusiness.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2024/2/8
 */
@Data
public class FireDataVO implements Serializable {

    private static final long serialVersionUID = 1625468943646L;

    private Long id;

    private float temperature;

    private float humidity;

    private float smokeConcentration;

    private float gasConcentration;

    private int illumination;

    private String deviceStatus;

    private Long deviceId;

    private int batteryLever;

    private int connectionStatus;

    private String alarmType;

    private Date alarmTime;

    private String alarmLocation;
}
