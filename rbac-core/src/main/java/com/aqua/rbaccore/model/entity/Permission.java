package com.aqua.rbaccore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限信息表
 * @TableName permission
 */
@TableName(value ="permission")
@Data
public class Permission implements Serializable {
    /**
     * 权限主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限描述
     */
    private String permissionDesc;

    /**
     * 权限表达
     */
    private String requiredPermission;

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
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}