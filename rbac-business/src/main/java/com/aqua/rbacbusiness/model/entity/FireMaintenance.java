package com.aqua.rbacbusiness.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消防设备日常维护记录表
 * @TableName fire_maintenance
 */
@TableName(value ="fire_maintenance")
@Data
public class FireMaintenance implements Serializable {
    /**
     * 日常维护记录表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 维护类型
     */
    private String maintenanceType;

    /**
     * 维护描述
     */
    private String maintenanceDesc;

    /**
     * 维护人员
     */
    private String maintenancePerson;

    /**
     * 记录维护的结果 (正常, 异常, 修复完成)
     */
    private String maintenanceResult;

    /**
     * 维护日期
     */
    private Date maintenanceDate;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}