package com.aqua.rbaccommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2024/2/6
 */
@Data
public class IdRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serivalVersionUID = 1L;
}
