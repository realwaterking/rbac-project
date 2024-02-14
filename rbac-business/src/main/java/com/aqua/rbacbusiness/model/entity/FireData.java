package com.aqua.rbacbusiness.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消防设备产生报警数据记录表
 * @TableName fire_facility_data
 */
@TableName(value ="fire_data")
@Data
public class FireData implements Serializable {
    /**
     * 报警数据表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 环境温度
     */
    private float temperature;

    /**
     * 湿度
     */
    private float humidity;

    /**
     * 烟雾浓度
     */
    private float smokeConcentration;

    /**
     * 气体浓度
     */
    private float gasConcentration;

    /**
     * 光照强度
     */
    private Integer illumination;

    /**
     * 设备状态
     */
    private String deviceStatus;

    /**
     * 产生数据的设备id
     */
    private Long deviceId;

    /**
     * 电池电量
     */
    private Integer batteryLever;

    /**
     * 连接状态
     */
    private Integer connectionStatus;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警时间
     */
    private Date alarmTime;

    /**
     * 报警位置
     */
    private String alarmLocation;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}