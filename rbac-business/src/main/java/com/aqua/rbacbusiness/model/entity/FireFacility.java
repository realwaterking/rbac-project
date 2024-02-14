package com.aqua.rbacbusiness.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消防设备信息表
 * @TableName fire_facility
 */
@TableName(value ="fire_facility")
@Data
public class FireFacility implements Serializable {
    /**
     * 消防设备主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消防设备名称
     */
    private String facilityName;

    /**
     * 消防设备所在地
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
     * 消防设备容量
     */
    private Integer capacity;

    /**
     * 消防设备安装日期
     */
    private Date installationdate;

    /**
     * 上次巡检日期
     */
    private Date lastinspectiondate;

    /**
     * 消防设施启用状态
     */
    private Integer isActive;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除（0-未删除， 1-已删除）
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}