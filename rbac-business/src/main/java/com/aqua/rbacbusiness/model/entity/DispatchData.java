package com.aqua.rbacbusiness.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出警记录表
 * @TableName dispatch_data
 */
@TableName(value ="dispatch_data")
@Data
public class DispatchData implements Serializable {
    /**
     * 出警信息表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 报警信息表主键
     */
    private Long fireDataId;

    /**
     * 出警人id
     */
    private Long userId;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 出警时间
     */
    private Date createTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}